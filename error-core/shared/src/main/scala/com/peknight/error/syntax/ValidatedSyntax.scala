package com.peknight.error.syntax

import cats.Show
import cats.data.Validated
import com.peknight.error.Error

trait ValidatedSyntax:
  extension [E, A] (validated: Validated[E, A])
    def asError: Validated[Error, A] = validated.leftMap(Error.apply)
    def label(label: => String): Validated[Error, A] = validated.leftMap(e => Error(e).label(label))
    def prependLabel(label: => String): Validated[Error, A] = validated.leftMap(e => Error(e).prependLabel(label))
    def message(message: => String): Validated[Error, A] = validated.leftMap(e => Error(e).message(message))
    def value[B: Show](value: => B): Validated[Error, A] = validated.leftMap(e => Error(e).value(value))
    def prepended[B: Show](value: => B): Validated[Error, A] = validated.leftMap(e => Error(e).prepended(value))
    def *:[B: Show](value: => B): Validated[Error, A] = validated.leftMap(e => Error(e).prepended(value))
    def to(error: => Error): Validated[Error, A] = validated.leftMap(e => Error(e).to(error))
  end extension
end ValidatedSyntax
object ValidatedSyntax extends ValidatedSyntax
