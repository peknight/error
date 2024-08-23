package com.peknight.error.syntax

import cats.data.Validated
import cats.data.Validated.{Invalid, Valid}
import com.peknight.error.Error

trait ValidatedSyntax:
  extension [E, A] (validated: Validated[E, A])
    def asError: Validated[Error, A] = validated.leftMap(Error.apply)
    def label(label: => String): Validated[Error, A] = validated.leftMap(e => Error(e).label(label))
    def prependLabel(label: => String): Validated[Error, A] = validated.leftMap(e => Error(e).prependLabel(label))
    def message(message: => String): Validated[Error, A] = validated.leftMap(e => Error(e).message(message))
    def value[T](value: => T): Validated[Error, A] = validated.leftMap(e => Error(e).value(value))
    def prepended[T](value: => T): Validated[Error, A] = validated.leftMap(e => Error(e).prepended(value))
    def *:[T](value: => T): Validated[Error, A] = validated.leftMap(e => Error(e).prepended(value))
    def to(error: => Error): Validated[Error, A] = validated.leftMap(e => Error(e).to(error))
  end extension
  extension [A] (flag: Boolean)
    def toValidated[E](e: E): Validated[E, Unit] = if flag then Valid(()) else Invalid(e)
  end extension
end ValidatedSyntax
object ValidatedSyntax extends ValidatedSyntax
