package com.peknight.error.syntax

import com.peknight.error.Error

trait EitherSyntax:
  extension [A, B] (either: Either[A, B])
    def pure: Either[Error, B] = either.left.map(Error.pure)
    def label(label: String): Either[Error, B] = either.left.map(e => Error.pure(e).label(label))
    def label(f: A => String): Either[Error, B] = either.left.map(e => Error.pure(e).label(f(e)))
    def message(message: String): Either[Error, B] = either.left.map(e => Error.pure(e).message(message))
    def message(f: A => String): Either[Error, B] = either.left.map(e => Error.pure(e).message(f(e)))
    def value[T](value: T): Either[Error, B] = either.left.map(e => Error.pure(e).value(value))
    def prepended[T](value: T): Either[Error, B] = either.left.map(e => Error.pure(e).prepended(value))
    def *:[T](value: T): Either[Error, B] = either.left.map(e => Error.pure(e).prepended(value))
    def to(error: Error): Either[Error, B] = either.left.map(e => Error.pure(e).to(error))
  end extension
end EitherSyntax
object EitherSyntax extends EitherSyntax
