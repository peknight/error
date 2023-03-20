package com.peknight.error

import cats.Monoid
import cats.data.NonEmptyList

sealed trait Error extends Serializable derives CanEqual

object Error:
  case object NoError extends Error

  case class StandardError[+E <: ErrorType, +Actual, +Expect, +Ext](errorType: E, label: String, actual: Actual,
                                                                    expect: Expect, ext: Ext, message: String,
                                                                    cause: Error) extends Error:
    override def toString: String = s"$errorType(label=$label${
      if actual.isInstanceOf[Unit] then "" else s", actual=$actual"
    }${
      if expect.isInstanceOf[Unit] then "" else s", expect=$expect"
    }${
      if ext.isInstanceOf[Unit] then "" else s", ext=$ext"
    }, message=$message${
      if cause == NoError then "" else s", cause=$cause"
    }"
  end StandardError

  case class Errors(errors: NonEmptyList[StandardError[_, _, _, _]]) extends Error:
    override def toString: String = errors.toList.mkString("Errors(", ", ", ")")
  end Errors

  object Errors:
    def apply(head: StandardError[_, _, _, _], tail: List[StandardError[_, _, _, _]]): Errors =
      Errors(NonEmptyList(head, tail))
    def apply(head: StandardError[_, _, _, _], tail: StandardError[_, _, _, _]*): Errors =
      Errors(NonEmptyList.of(head, tail*))
  end Errors

  def empty: Error = NoError

  def apply[E <: ErrorType, Actual, Expect, Ext](errorType: E, label: String, actual: Actual, expect: Expect, ext: Ext,
                                                 message: String, cause: Error): Error =
    StandardError(errorType, label, actual, expect, ext, message, cause)

  def apply(errors: NonEmptyList[StandardError[_, _, _, _]]): Error = Errors(errors)
  def apply(head: StandardError[_, _, _, _], tail: List[StandardError[_, _, _, _]]): Error = Errors(head, tail)
  def apply(head: StandardError[_, _, _, _], tail: StandardError[_, _, _, _]*): Error = Errors(head, tail*)

  given Monoid[Error] with
    def empty: Error = NoError
    def combine(x: Error, y: Error): Error = (x, y) match
      case (NoError, yError) => yError
      case (xError, NoError) => xError
      case (Errors(xErrors), Errors(yErrors)) => Errors(xErrors ++ yErrors.toList)
      case (Errors(xErrors), yError: StandardError[_, _, _, _]) =>Errors(xErrors.head, xErrors.tail :+ yError)
      case (xError: StandardError[_, _, _, _], Errors(yErrors)) => Errors(xError, yErrors.toList)
      case (xError: StandardError[_, _, _, _], yError: StandardError[_, _, _, _]) => Errors(xError, yError)
  end given

end Error
