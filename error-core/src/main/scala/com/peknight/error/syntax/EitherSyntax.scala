package com.peknight.error.syntax

import cats.Show
import cats.data.Ior
import com.peknight.error.Error

trait EitherSyntax:
  extension [A, B] (either: Either[A, B])
    def asError: Either[Error, B] = either.left.map(Error.apply)
    def asErrorIor: Ior[Error, B] = either.fold(a => Ior.left(Error(a)), Ior.right)
    def label(label: => String): Either[Error, B] = either.left.map(e => Error(e).label(label))
    def prependLabel(label: => String): Either[Error, B] = either.left.map(e => Error(e).prependLabel(label))
    def message(message: => String): Either[Error, B] = either.left.map(e => Error(e).message(message))
    def value[C: Show](value: => C): Either[Error, B] = either.left.map(e => Error(e).value(value))
    def prepended[C: Show](value: => C): Either[Error, B] = either.left.map(e => Error(e).prepended(value))
    def *:[C: Show](value: => C): Either[Error, B] = either.left.map(e => Error(e).prepended(value))
    def to(error: => Error): Either[Error, B] = either.left.map(e => Error(e).to(error))
  end extension
end EitherSyntax
object EitherSyntax extends EitherSyntax
