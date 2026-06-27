package com.peknight.error.syntax

import cats.Show
import cats.data.Ior
import com.peknight.error.Error

trait IorSyntax:
  extension [A, B] (ior: Ior[A, B])
    def asError: Ior[Error, B] = ior.leftMap(Error.apply)
    def label(label: => String): Ior[Error, B] = ior.leftMap(e => Error(e).label(label))
    def prependLabel(label: => String): Ior[Error, B] = ior.leftMap(e => Error(e).prependLabel(label))
    def message(message: => String): Ior[Error, B] = ior.leftMap(e => Error(e).message(message))
    def value[C: Show](value: => C): Ior[Error, B] = ior.leftMap(e => Error(e).value(value))
    def prepended[C: Show](value: => C): Ior[Error, B] = ior.leftMap(e => Error(e).prepended(value))
    def *:[C: Show](value: => C): Ior[Error, B] = ior.leftMap(e => Error(e).prepended(value))
    def toError(error: => Error): Ior[Error, B] = ior.leftMap(e => Error(e).to(error))
  end extension
end IorSyntax
object IorSyntax extends IorSyntax
