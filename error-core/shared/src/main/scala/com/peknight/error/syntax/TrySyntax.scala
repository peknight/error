package com.peknight.error.syntax

import cats.Show
import com.peknight.error.Error

import scala.util.Try

trait TrySyntax:
  extension [A] (`try`: Try[A])
    def asError: Either[Error, A] = `try`.toEither.left.map(Error.apply)
    def label(label: => String): Either[Error, A] = `try`.toEither.left.map(e => Error(e).label(label))
    def prependLabel(label: => String): Either[Error, A] = `try`.toEither.left.map(e => Error(e).prependLabel(label))
    def message(message: => String): Either[Error, A] = `try`.toEither.left.map(e => Error(e).message(message))
    def value[B: Show](value: => B): Either[Error, A] = `try`.toEither.left.map(e => Error(e).value(value))
    def prepended[B: Show](value: => B): Either[Error, A] = `try`.toEither.left.map(e => Error(e).prepended(value))
    def *:[B: Show](value: => B): Either[Error, A] = `try`.toEither.left.map(e => Error(e).prepended(value))
    def to(error: => Error): Either[Error, A] = `try`.toEither.left.map(e => Error(e).to(error))
  end extension
end TrySyntax
object TrySyntax extends TrySyntax
