package com.peknight.error

import cats.data.NonEmptyList
import cats.{Monoid, Show}

sealed trait Error extends Serializable derives CanEqual

object Error:
  case object EmptyError extends Error

  case class StandardError[T <: ErrorType, V](errorType: T, value: V, cause: Error, show: ErrorShow[T, V]) extends Error

  case class Errors(errors: NonEmptyList[StandardError[_, _]]) extends Error

  object Errors:
    def apply(head: StandardError[_, _], tail: List[StandardError[_, _]]): Errors = Errors(NonEmptyList(head, tail))
    def apply(head: StandardError[_, _], tail: StandardError[_, _]*): Errors = Errors(NonEmptyList.of(head, tail*))
  end Errors

  def empty: Error = EmptyError

  def apply[T <: ErrorType, V](errorType: T, value: V, cause: Error, show: ErrorShow[T, V]): Error =
    StandardError(errorType, value, cause, show)

  def apply(errors: NonEmptyList[StandardError[_, _]]): Error = Errors(errors)
  def apply(head: StandardError[_, _], tail: List[StandardError[_, _]]): Error = Errors(head, tail)
  def apply(head: StandardError[_, _], tail: StandardError[_, _]*): Error = Errors(head, tail*)

  given Monoid[Error] with
    def empty: Error = EmptyError
    def combine(x: Error, y: Error): Error = (x, y) match
      case (EmptyError, yError) => yError
      case (xError, EmptyError) => xError
      case (Errors(xErrors), Errors(yErrors)) => Errors(xErrors ++ yErrors.toList)
      case (Errors(xErrors), yError: StandardError[_, _]) =>Errors(xErrors.head, xErrors.tail :+ yError)
      case (xError: StandardError[_, _], Errors(yErrors)) => Errors(xError, yErrors.toList)
      case (xError: StandardError[_, _], yError: StandardError[_, _]) => Errors(xError, yError)
  end given

  given Show[Error] with
    def show(error: Error): String =
      error match
        case EmptyError => "No error"
        case StandardError(errorType, value, EmptyError, errorShow) => s"${errorShow.show(errorType, value)}"
        case StandardError(errorType, value, cause, errorShow) => s"${errorShow.show(errorType, value)} caused by ${show(cause)}"
        case Errors(errors) => errors.toList.map(show).mkString("(", ", ", ")")
  end given
end Error
