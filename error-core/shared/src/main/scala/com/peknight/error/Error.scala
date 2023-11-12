package com.peknight.error

import cats.Monoid
import cats.data.NonEmptyList
import cats.syntax.option.*
import com.peknight.error.Error.{Common, Pure, pure, pureErrorType}

import scala.annotation.tailrec

trait Error extends Serializable derives CanEqual:
  protected def labelOption: Option[String] = None
  protected def messageOption: Option[String] = None
  protected def labelMessage(label: String): Option[String] = None
  def message: String =
    messageOption.filter(_.nonEmpty) match
      case Some(m) => m
      case _ => labelOption.filter(_.nonEmpty).fold(pureErrorType(this))(label =>
        labelMessage(label).filter(_.nonEmpty).getOrElse(s"$label: ${pureErrorType(this)}")
      )
  end message
  def messages: List[String] = List(message)
  def cause: Option[Error] = None
  def label(label: String): Error =
    val labelOpt = label.some.filter(_.nonEmpty)
    pure(this) match
      case Pure(e) => Common(e, labelOpt, cause = cause)
      case c @ Common(_, _, _, _, _) => c.copy(labelOption = labelOpt)
      case _ => Common(this, labelOpt, cause = cause)

  def message(message: String): Error =
    val messageOpt = message.some.filter(_.nonEmpty)
    pure(this) match
      case Pure(e) => Common(e, messageOption = messageOpt, cause = cause)
      case c @ Common(_, _, _, _, _) => c.copy(messageOption = messageOpt)
      case _ => Common(this, messageOption = messageOpt, cause = cause)

  def value[T](value: T): Error = pure(this) match
    case Pure(e) => Common(e, value = value.some, cause = cause)
    case c @ Common(_, _, _, _, _) => c.copy(value = value.some)
    case _ => Common(this, value = value.some, cause = cause)

  def prepended[T](value: T): Error = pure(this) match
    case Pure(e) => Common(e, value = value.some, cause = cause)
    case c @ Common(_, _, _, None, _) => c.copy(value = value.some)
    case c @ Common(_, _, _, Some(t: Tuple), _) => c.copy(value = (value *: t).some)
    case c @ Common(_, _, _, Some(v), _) => c.copy(value = (value *: v *: EmptyTuple).some)
    case _ => Common(this, value = value.some, cause = cause)

  def *:[T](value: T): Error = prepended(value)

  def to(error: Error): Error = Common(error, cause = Some(this))

end Error

object Error:
  private[error] def errorType[E](e: E): String =
    e.getClass.getSimpleName.replaceAll("\\$", "")

  private[error] def pureErrorType[E](e: E): String =
    pure(e) match
      case Pure(error) => errorType(error)
      case Common(error, _, _, _, _) => errorType(error)
      case _ => errorType(e)

  private[error] case object Success extends Error

  private[error] case class Pure[+E](error: E) extends Error

  trait Label extends Error:
    def label: String
    override def labelOption: Option[String] = label.some
  end Label

  trait Value[+A] extends Error:
    def value: A
  end Value

  trait LabelValue[+A] extends Label with Value[A]

  private[error] case class Common[+E, +T](error: E, override val labelOption: Option[String] = None,
                                           override val messageOption: Option[String] = None, value: Option[T] = None,
                                           override val cause: Option[Error] = None) extends Value[Option[T]]

  private[error] case class Errors(errors: NonEmptyList[Error]) extends Error:
    override def message: String = messages.mkString(", ")
    override def messages: List[String] = errors.toList.flatMap(_.messages)
  end Errors
  object Errors:
    private[error] def apply(head: Error, tail: List[Error]): Errors = Errors(NonEmptyList(head, tail))
    private[error] def apply(head: Error, tail: Error*): Errors = Errors(NonEmptyList.of(head, tail*))
  end Errors

  def success: Error = Success

  @tailrec def pure[E](error: E): Error =
    error match
      case Pure(e) => pure(e)
      case e: Error => e
      case _ => Pure(error)

  def apply(errors: NonEmptyList[Error]): Error = Errors(errors)
  def apply(head: Error, tail: List[Error]): Error = Errors(head, tail)
  def apply(head: Error, tail: Error*): Error = Errors(head, tail*)

  given Monoid[Error] with
    def empty: Error = Success
    def combine(x: Error, y: Error): Error = (pure(x), pure(y)) match
      case (Success, yError) => yError
      case (xError, Success) => xError
      case (Errors(xErrors), Errors(yErrors)) => Errors(xErrors ++ yErrors.toList)
      case (Errors(xErrors), yError) => Errors(xErrors.head, xErrors.tail :+ yError)
      case (xError, Errors(yErrors)) => Errors(xError, yErrors.toList)
      case (xError, yError) => Errors(xError, yError)
  end given
end Error
