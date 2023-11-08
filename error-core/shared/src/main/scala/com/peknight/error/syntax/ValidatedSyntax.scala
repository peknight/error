package com.peknight.error.syntax

import cats.data.Validated
import com.peknight.error.Error

trait ValidatedSyntax:
  extension [E, A] (validated: Validated[E, A])
    def pure: Validated[Error, A] = validated.leftMap(Error.pure)
    def label(label: String): Validated[Error, A] = validated.leftMap(e => Error.pure(e).label(label))
    def label(f: E => String): Validated[Error, A] = validated.leftMap(e => Error.pure(e).label(f(e)))
    def message(message: String): Validated[Error, A] = validated.leftMap(e => Error.pure(e).message(message))
    def message(f: E => String): Validated[Error, A] = validated.leftMap(e => Error.pure(e).message(f(e)))
    def value[T](value: T): Validated[Error, A] = validated.leftMap(e => Error.pure(e).value(value))
    def prepended[T](value: T): Validated[Error, A] = validated.leftMap(e => Error.pure(e).prepended(value))
    def *:[T](value: T): Validated[Error, A] = validated.leftMap(e => Error.pure(e).prepended(value))
    def to(error: Error): Validated[Error, A] = validated.leftMap(e => Error.pure(e).to(error))
  end extension
end ValidatedSyntax
object ValidatedSyntax extends ValidatedSyntax
