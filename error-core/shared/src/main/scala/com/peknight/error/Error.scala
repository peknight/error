package com.peknight.error

import cats.data.NonEmptyList
import cats.syntax.option.*
import com.peknight.error.Error.{Common, Pure, pureMessage}
import com.peknight.error.instances.ErrorInstances

import scala.annotation.tailrec

trait Error extends Exception with Serializable derives CanEqual:
  def message: String = labelMessage(None)
  def messages: List[String] = List(message)
  def cause: Option[Error] = None
  protected def pure: Error = Error.pure(this)
  protected def labelOption: Option[String] = None
  protected def messageOption: Option[String] = None
  protected def lowPriorityLabelMessage(label: String): Option[String] = None
  protected def lowPriorityMessage: Option[String] = None
  protected def labelMessage(outerLabelOption: Option[String]): String =
    (outerLabelOption.filter(_.nonEmpty), messageOption.filter(_.nonEmpty)) match
      case (Some(outerLabel), Some(m)) => s"$outerLabel: $m"
      case (_, Some(m)) => m
      case _ =>
        val labelOpt: Option[String] = outerLabelOption.filter(_.nonEmpty)
          .fold(labelOption.filter(_.nonEmpty))(outerLabel =>
            labelOption.filter(_.nonEmpty).fold(outerLabel.some)(currentLabel => s"$outerLabel.$currentLabel".some)
          )
        pure match
          case Common(e: Error, _, _, _, _) => e.labelMessage(labelOpt)
          case _ => labelOpt.fold(lowPriorityMessage.filter(_.nonEmpty).getOrElse(pureMessage(this)))(label =>
            lowPriorityLabelMessage(label).filter(_.nonEmpty)
              .orElse(lowPriorityMessage.filter(_.nonEmpty).map(lpm => s"$label: $lpm"))
              .getOrElse(s"$label: ${pureMessage(this)}")
          )
  end labelMessage

  def label(label: String): Error =
    val labelOpt = label.some.filter(_.nonEmpty)
    if labelOpt == labelOption then this
    else
      pure match
        case Pure(e) => Common(e, labelOpt, cause = cause)
        case c @ Common(_, _, _, _, _) => c.copy(labelOption = labelOpt)
        case _ => Common(this, labelOpt, cause = cause)
  end label

  def prependLabel(prependLabel: String): Error =
    prependLabel.some.filter(_.nonEmpty) match
      case None => this
      case Some(prepend) => label(labelOption.fold(prepend)(lab => s"$prepend.$lab"))
  end prependLabel

  def message(message: String): Error =
    val messageOpt = message.some.filter(_.nonEmpty)
    if messageOpt == messageOption then this
    else
      pure match
        case Pure(e) => Common(e, messageOption = messageOpt, cause = cause)
        case c @ Common(_, _, _, _, _) => c.copy(messageOption = messageOpt)
        case _ => Common(this, messageOption = messageOpt, cause = cause)
  end message

  def value[T](value: T): Error = pure match
    case Pure(e) => Common(e, value = value.some, cause = cause)
    case c @ Common(_, _, _, _, _) => c.copy(value = value.some)
    case _ => Common(this, value = value.some, cause = cause)

  def prepended[T](value: T): Error = pure match
    case Pure(e) => Common(e, value = value.some, cause = cause)
    case c @ Common(_, _, _, None, _) => c.copy(value = value.some)
    case c @ Common(_, _, _, Some(t: Tuple), _) => c.copy(value = (value *: t).some)
    case c @ Common(_, _, _, Some(v), _) => c.copy(value = (value *: v *: EmptyTuple).some)
    case _ => Common(this, value = value.some, cause = cause)

  def *:[T](value: T): Error = prepended(value)

  def to(error: Error): Error = Common(error, cause = Some(this))

  override final def fillInStackTrace(): Throwable = this

  override final def getMessage: String = message

  override def getCause: Throwable = cause.filter(_.ne(this)).orNull
end Error

object Error extends ErrorInstances:
  private[error] case object Success extends Error
  trait Lift[+E] extends Error:
    def error: E
  end Lift
  private[error] case class Pure[+E](error: E) extends Lift[E]
  private[error] case class Errors(errors: NonEmptyList[Error]) extends Error:
    override protected def lowPriorityMessage: Option[String] = messages.mkString(", ").some
    override def messages: List[String] = errors.toList.flatMap(_.messages)
  end Errors
  object Errors:
    private[error] def apply(head: Error, tail: List[Error]): Errors = Errors(NonEmptyList(head, tail))
    private[error] def apply(head: Error, tail: Error*): Errors = Errors(NonEmptyList.of(head, tail *))
  end Errors
  trait Label extends Error:
    def label: String
    override def labelOption: Option[String] = label.some
  end Label
  trait Value[+A] extends Error:
    def value: A
  end Value
  trait LabelValue[+A] extends Label with Value[A]
  private[error] case class Common[+E, +T](
    error: E,
    override val labelOption: Option[String] = None,
    override val messageOption: Option[String] = None,
    value: Option[T] = None,
    override val cause: Option[Error] = None
  ) extends Lift[E] with Value[Option[T]]

  def success: Error = Success

  @tailrec def pure[E](error: E): Error =
    error match
      case Pure(e) => pure(e)
      case Errors(NonEmptyList(e, Nil)) => pure(e)
      case e: Error => e
      case _ => Pure(error)

  def apply: Error = success
  def apply[E](error: E): Error =
    error match
      case NonEmptyList(head, tail) => apply(head, tail)
      case _ => pure(error)
  def apply[E](head: E, tail: E*): Error = apply(head, tail.toList)
  def apply[E](head: E, tail: List[E]): Error = if tail.isEmpty then pure(head) else Errors(pure(head), tail.map(pure))

  private[error] def errorType[E](e: E): String =
    e.getClass.getSimpleName.replaceAll("\\$", "")

  @tailrec private[error] def pureMessage[E](e: E): String =
    pure(e) match
      case Pure(m: String) => m
      case Pure(t: Throwable) => t.getMessage
      case Pure(error) => errorType(error)
      case err: Lift[?] => err.error match
        case m: String => m
        case t: Throwable => t.getMessage
        case error => pureMessage(error)
      case _ => errorType(e)
end Error
