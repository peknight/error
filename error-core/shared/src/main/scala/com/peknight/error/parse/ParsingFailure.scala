package com.peknight.error.parse

import cats.Monoid
import cats.data.NonEmptyList
import cats.syntax.option.*
import com.peknight.error.Error
import com.peknight.error.parse.ParsingFailure.{Common, Pure}

import scala.annotation.tailrec

trait ParsingFailure extends Error:
  override protected def pure: ParsingFailure = ParsingFailure.pure(this)
  override protected def lowPriorityLabelMessage(label: String): Option[String] = Some(s"parse $label failed")
  override def label(label: String): ParsingFailure =
    val labelOpt = label.some.filter(_.nonEmpty)
    if labelOpt == labelOption then this
    else
      pure match
        case Pure(e) => Common(e, labelOpt, cause = cause)
        case c @ Common(_, _, _, _, _) => c.copy(labelOption = labelOpt)
        case _ => Common(this, labelOpt, cause = cause)
  end label

  override def prependLabel(prependLabel: String): ParsingFailure =
    prependLabel.some.filter(_.nonEmpty) match
      case None => this
      case Some(prepend) => label(labelOption.fold(prepend)(lab => s"$prepend.$lab"))
  end prependLabel

  override def message(message: String): ParsingFailure =
    val messageOpt = message.some.filter(_.nonEmpty)
    if messageOpt == messageOption then this
    else
      pure match
        case Pure(e) => Common(e, messageOption = messageOpt, cause = cause)
        case c @ Common(_, _, _, _, _) => c.copy(messageOption = messageOpt)
        case _ => Common(this, messageOption = messageOpt, cause = cause)
  end message

  override def value[T](value: T): ParsingFailure = pure match
    case Pure(e) => Common(e, value = value.some, cause = cause)
    case c @ Common(_, _, _, _, _) => c.copy(value = value.some)
    case _ => Common(this, value = value.some, cause = cause)

  override def prepended[T](value: T): ParsingFailure = pure match
    case Pure(e) => Common(e, value = value.some, cause = cause)
    case c @ Common(_, _, _, None, _) => c.copy(value = value.some)
    case c @ Common(_, _, _, Some(t: Tuple), _) => c.copy(value = (value *: t).some)
    case c @ Common(_, _, _, Some(v), _) => c.copy(value = (value *: v *: EmptyTuple).some)
    case _ => Common(this, value = value.some, cause = cause)

  override def *:[T](value: T): ParsingFailure = prepended(value)
end ParsingFailure
object ParsingFailure extends ParsingFailure:
  private[error] object Success extends ParsingFailure with com.peknight.error.Success
  private[error] case class Pure[+E](error: E) extends ParsingFailure with com.peknight.error.Pure[E]
  private[error] case class Errors(errors: NonEmptyList[ParsingFailure]) extends ParsingFailure
    with com.peknight.error.Errors[ParsingFailure]
  private[error] case class Common[+E, +T](
    error: E,
    override val labelOption: Option[String] = None,
    override val messageOption: Option[String] = None,
    value: Option[T] = None,
    override val cause: Option[Error] = None
  ) extends ParsingFailure with com.peknight.error.Common[E, T]

  def success: ParsingFailure = Success

  @tailrec def pure[E](error: E): ParsingFailure =
    error match
      case e: (com.peknight.error.Pure[?] & ParsingFailure) => pure(e.error)
      case e: (com.peknight.error.Errors[?] & ParsingFailure) if e.errors.tail.isEmpty => pure(e.errors.head)
      case e: ParsingFailure => e
      case _ => Pure(error)

  def apply: ParsingFailure = success
  def apply[E](error: E): ParsingFailure =
    error match
      case NonEmptyList(head, tail) => apply(head, tail)
      case head :: Nil => pure(head)
      case head :: tail => apply(head, tail)
      case _ => pure(error)
  def apply[E](head: E, tail: E*): ParsingFailure = apply(head, tail.toList)
  def apply[E](head: E, tail: List[E]): ParsingFailure =
    if tail.isEmpty then pure(head)
    else Errors(NonEmptyList(pure(head), tail.map(pure)))

  given Monoid[ParsingFailure] with
    def empty: ParsingFailure = Success
    def combine(x: ParsingFailure, y: ParsingFailure): ParsingFailure = (pure(x), pure(y)) match
      case (Success, yError) => yError
      case (xError, Success) => xError
      case (Errors(xErrors), Errors(yErrors)) => Errors(xErrors ++ yErrors.toList)
      case (Errors(xErrors), yError) => Errors(NonEmptyList(xErrors.head, xErrors.tail :+ yError))
      case (xError, Errors(yErrors)) => Errors(NonEmptyList(xError, yErrors.toList))
      case (xError, yError) => Errors(NonEmptyList(xError, List(yError)))
  end given
end ParsingFailure
