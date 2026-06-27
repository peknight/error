package com.peknight.error.syntax

import cats.data.Ior
import cats.syntax.functor.*
import cats.{Functor, Show}
import com.peknight.error.Error

trait IorFSyntax:
  extension [F[_], A, B] (fi: F[Ior[A, B]])
    def asError(using Functor[F]): F[Ior[Error, B]] = fi.map(_.leftMap(Error.apply))
    def label(label: => String)(using Functor[F]): F[Ior[Error, B]] = fi.map(_.leftMap(e => Error(e).label(label)))
    def prependLabel(label: => String)(using Functor[F]): F[Ior[Error, B]] =
      fi.map(_.leftMap(e => Error(e).prependLabel(label)))
    def message(message: => String)(using Functor[F]): F[Ior[Error, B]] =
      fi.map(_.leftMap(e => Error(e).message(message)))
    def value[C](value: => C)(using Functor[F], Show[C]): F[Ior[Error, B]] = fi.map(_.leftMap(e => Error(e).value(value)))
    def prepended[C](value: => C)(using Functor[F], Show[C]): F[Ior[Error, B]] =
      fi.map(_.leftMap(e => Error(e).prepended(value)))
    def *:[C](value: => C)(using Functor[F], Show[C]): F[Ior[Error, B]] = fi.map(_.leftMap(e => Error(e).prepended(value)))
    def toError(error: => Error)(using Functor[F]): F[Ior[Error, B]] = fi.map(_.leftMap(e => Error(e).to(error)))
  end extension
end IorFSyntax
object IorFSyntax extends IorFSyntax
