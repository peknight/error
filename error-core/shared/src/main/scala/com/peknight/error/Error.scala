package com.peknight.error

import cats.data.NonEmptyList
import cats.syntax.option.*
import cats.syntax.show.*
import cats.{Eval, Show}
import com.peknight.cats.instances.clazz.clazz.given
import com.peknight.error.Error.{Common, Pure, pureMessage}
import com.peknight.error.instances.ErrorInstances
import com.peknight.error.std.{JavaThrowable, StandardError}

import scala.annotation.tailrec
import scala.reflect.ClassTag

trait Error extends Exception with Serializable derives CanEqual:
  def message: String = labelMessage(None)
  def messages: List[String] = List(message)
  def cause: Option[Error] = None
  def success: Boolean =
    Error.base(this) match
      case error: Success => true
      case _ => false
  def errorType: String = Error.errorType(this)
  def throwable: Option[Throwable] = Error.throwable(this)
  def showValue: Option[String] = None
  def standard: StandardError = StandardError(errorType, message)
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
        Error.pure(this) match
          case e: Lift[?] if e.error.isInstanceOf[Error] => e.error.asInstanceOf[Error].labelMessage(labelOpt)
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

  def value[A](value: A)(using show: Show[A]): Error = pure match
    case Pure(e) => Common(e, valueShow = (value, Eval.later(show.show(value))).some, cause = cause)
    case c @ Common(_, _, _, _, _) => c.copy(valueShow = (value, Eval.later(show.show(value))).some)
    case _ => Common(this, valueShow = (value, Eval.later(show.show(value))).some, cause = cause)

  def prepended[A](value: A)(using show: Show[A]): Error = pure match
    case Pure(e) => Common(e, valueShow = (value, Eval.later(show.show(value))).some, cause = cause)
    case c @ Common(_, _, _, None, _) => c.copy(valueShow = (value, Eval.later(show.show(value))).some)
    case c @ Common(_, _, _, Some((t: Tuple, eval)), _) => 
      c.copy(valueShow = (value *: t, eval.flatMap(s => Eval.later(s"${show.show(value)},$s"))).some)
    case c @ Common(_, _, _, Some((v, eval)), _) => 
      c.copy(valueShow = (value *: v *: EmptyTuple, eval.flatMap(s => Eval.later(s"${show.show(value)},$s"))).some)
    case _ => Common(this, valueShow = (value, Eval.later(show.show(value))).some, cause = cause)

  def *:[A](value: A)(using show: Show[A]): Error = prepended(value)
  def to[E](error: E): Error = Common(error, cause = Some(this))
  override final def fillInStackTrace(): Throwable = this
  override final def getMessage: String = message
  override def getCause: Throwable = cause.filter(_.ne(this)).orNull
end Error

object Error extends Error with ErrorInstances:
  private[error] case class Pure[+E](error: E) extends com.peknight.error.Pure[E]
  private[error] case class Errors(errors: NonEmptyList[Error]) extends com.peknight.error.Errors[Error]
  private[error] case class Common[+E, A](
                                           error: E,
                                           override val labelOption: Option[String] = None,
                                           override val messageOption: Option[String] = None,
                                           valueShow: Option[(A, Eval[String])] = None,
                                           override val cause: Option[Error] = None
  ) extends com.peknight.error.Common[E, A]

  @tailrec def pure[E](error: E): Error =
    error match
      case e: com.peknight.error.Errors[?] if e.errors.tail.isEmpty => pure(e.errors.head)
      case e: com.peknight.error.Pure[?] => pure(e.error)
      case e: Error => e
      case e: Throwable => JavaThrowable(e)
      case _ => Pure(error)

  @tailrec def base[E](error: E): Error =
    error match
      case e: com.peknight.error.Errors[?] if e.errors.tail.isEmpty => base(e.errors.head)
      case e: com.peknight.error.Lift[?] => base(e.error)
      case e: Error => e
      case e: Throwable => JavaThrowable(e)
      case _ => Pure(error)

  def throwable[E](error: E): Option[Throwable] =
    base(error) match
      case e: JavaThrowable[?] => Some(e.error)
      case _ => None

  def apply: Error = Success
  def apply[E](error: E): Error =
    given [A]: CanEqual[List[A], E] = CanEqual.derived
    error match
      case NonEmptyList(head, tail) => apply(head, tail)
      case Nil => Success
      case head :: Nil => pure(head)
      case head :: tail => apply(head, tail)
      case _ => pure(error)
  def apply[E](head: E, tail: E*): Error = apply(head, tail.toList)
  def apply[E](head: E, tail: List[E]): Error =
    if tail.isEmpty then pure(head)
    else Errors(NonEmptyList(pure(head), tail.map(pure)))

  def showClassTag[E](using classTag: ClassTag[E]): String = classTag.runtimeClass.show
  def showType[E](e: E): String = e.getClass.show
  def showClass[E](clazz: Class[E]): String = clazz.show

  def errorType[E](e: E): String =
    base(e) match
      case err: Lift[?] if err.error.isInstanceOf[String] => err.error.asInstanceOf[String]
      case err: Lift[?] => err.error match
        case m: String => m
        case error => showType(error)
      case err => showType(err)

  def pureMessage[E](e: E): String =
    base(e) match
      case err: Lift[?] => err.error match
        case m: String => m
        case t: Throwable => s"${showType(t)}:${t.getMessage}"
        case error => s"${showType(error)}:$error"
      case err => showType(err)
end Error
