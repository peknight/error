package com.peknight.error

import cats.Monoid
import cats.data.NonEmptyList
import com.peknight.error.Error.StandardError

sealed trait Error extends Serializable derives CanEqual:

  def to[E <: ErrorType, Actual, Expect, Ext <: Tuple](errorType: E, label: String, actual: Actual, expect: Expect,
                                                       message: String, ext: Ext = EmptyTuple.asInstanceOf[Ext])
  : StandardError[E, Actual, Expect, Ext] =
    StandardError(errorType, label, actual, expect, message, ext, this)
end Error

object Error:
  case object NoError extends Error

  case class StandardError[+E <: ErrorType, +Actual, +Expect, +Ext <: Tuple](errorType: E, label: String, actual: Actual,
                                                                             expect: Expect, message: String,
                                                                             ext: Ext = EmptyTuple.asInstanceOf[Ext],
                                                                             cause: Error = NoError) extends Error:
    def prepended[A](a: A): StandardError[E, Actual, Expect, A *: Ext] = copy(ext = a *: ext)

    def *:[A](a: A): StandardError[E, Actual, Expect, A *: Ext] = prepended(a)

    override def toString: String =
      List(
        if label.isEmpty then "" else s"label=$label",
        if actual.isInstanceOf[Unit] then "" else s"actual=$actual",
        if expect.isInstanceOf[Unit] then "" else s"expect=$expect",
        if message.isEmpty then "" else s"message=$message",
        if ext.isInstanceOf[EmptyTuple] then "" else s"ext=$ext",
        if cause == NoError then "" else s"cause=$cause"
      ).filter(_.nonEmpty).mkString(s"$errorType(", ", ", ")")
  end StandardError

  case class Errors(errors: NonEmptyList[StdError]) extends Error:
    override def toString: String = errors.toList.mkString("Errors(", ", ", ")")
  end Errors

  object Errors:
    def apply(head: StdError, tail: List[StdError]): Errors = Errors(NonEmptyList(head, tail))
    def apply(head: StdError, tail: StdError*): Errors = Errors(NonEmptyList.of(head, tail*))
  end Errors

  def empty: Error = NoError

  def apply[E <: ErrorType, Actual, Expect, Ext <: Tuple](errorType: E, label: String, actual: Actual, expect: Expect,
                                                          message: String, ext: Ext = EmptyTuple.asInstanceOf[Ext],
                                                          cause: Error = NoError): Error =
    StandardError(errorType, label, actual, expect, message, ext, cause)

  def apply(errors: NonEmptyList[StdError]): Error = Errors(errors)
  def apply(head: StdError, tail: List[StdError]): Error = Errors(head, tail)
  def apply(head: StdError, tail: StdError*): Error = Errors(head, tail*)

  given Monoid[Error] with
    def empty: Error = NoError
    def combine(x: Error, y: Error): Error = (x, y) match
      case (NoError, yError) => yError
      case (xError, NoError) => xError
      case (Errors(xErrors), Errors(yErrors)) => Errors(xErrors ++ yErrors.toList)
      case (Errors(xErrors), yError: StdError) => Errors(xErrors.head, xErrors.tail :+ yError)
      case (xError: StdError, Errors(yErrors)) => Errors(xError, yErrors.toList)
      case (xError: StdError, yError: StdError) => Errors(xError, yError)
  end given

end Error
