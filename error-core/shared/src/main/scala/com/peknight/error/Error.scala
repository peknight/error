package com.peknight.error

import cats.Monoid
import cats.data.NonEmptyList
import com.peknight.error.Error.{Common, Pure, pure}

import scala.annotation.tailrec

trait Error extends Exception with Serializable derives CanEqual:
  def label(label: String): Error = pure(this) match
    case Pure(e) => Common(e, label, "")
    case c @ Common(_, _, _, _, _) => c.copy(label = label)
    case _ => Common(this, label, "")

  def message(message: String): Error = pure(this) match
    case Pure(e) => Common(e, "", message)
    case c @ Common(_, _, _, _, _) => c.copy(message = message)
    case _ => Common(this, "", message)

  def value[T](value: T): Error = pure(this) match
    case Pure(e) => Common(e, "", "", Some(value))
    case c @ Common(_, _, _, _, _) => c.copy(value = Some(value))
    case _ => Common(this, "", "", Some(value))

  def prepended[T](value: T): Error = pure(this) match
    case Pure(e) => Common(e, "", "", Some(value))
    case c @ Common(_, _, _, None, _) => c.copy(value = Some(value))
    case c @ Common(_, _, _, Some(t: Tuple), _) => c.copy(value = Some(value *: t))
    case c @ Common(_, _, _, Some(v), _) => c.copy(value = Some(value *: v *: EmptyTuple))
    case _ => Common(this, "", "", Some(value))

  def *:[T](value: T): Error = prepended(value)

  def to(error: Error): Error = Common(error, "", "", None, Some(this))

end Error

object Error:
  private[error] case object Success extends Error

  private[error] case class Pure[+E](error: E) extends Error

  private[error] case class Common[+E, +T](error: E, label: String, message: String, value: Option[T] = None,
                                          cause: Option[Error] = None) extends Error

  private[error] case class Errors(errors: NonEmptyList[Error]) extends Error
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
    def combine(x: Error, y: Error): Error = (x, y) match
      case (Success, yError) => yError
      case (xError, Success) => xError
      case (Errors(xErrors), Errors(yErrors)) => Errors(xErrors ++ yErrors.toList)
      case (Errors(xErrors), yError) => Errors(xErrors.head, xErrors.tail :+ yError)
      case (xError, Errors(yErrors)) => Errors(xError, yErrors.toList)
      case (xError, yError) => Errors(xError, yError)
  end given
end Error
