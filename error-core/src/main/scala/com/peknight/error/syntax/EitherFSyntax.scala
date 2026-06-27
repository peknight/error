package com.peknight.error.syntax

import cats.data.Ior
import cats.syntax.functor.*
import cats.{Functor, Show}
import com.peknight.error.Error

trait EitherFSyntax:
  extension [F[_], A, B] (fe: F[Either[A, B]])
    def asError(using Functor[F]): F[Either[Error, B]] = fe.map(_.left.map(Error.apply))
    def asErrorIor(using Functor[F]): F[Ior[Error, B]] = fe.map(_.fold(a => Ior.left(Error(a)), Ior.right))
    def label(label: => String)(using Functor[F]): F[Either[Error, B]] = fe.map(_.left.map(e => Error(e).label(label)))
    def prependLabel(label: => String)(using Functor[F]): F[Either[Error, B]] =
      fe.map(_.left.map(e => Error(e).prependLabel(label)))
    def message(message: => String)(using Functor[F]): F[Either[Error, B]] =
      fe.map(_.left.map(e => Error(e).message(message)))
    def value[C](value: => C)(using Functor[F], Show[C]): F[Either[Error, B]] = fe.map(_.left.map(e => Error(e).value(value)))
    def prepended[C](value: => C)(using Functor[F], Show[C]): F[Either[Error, B]] =
      fe.map(_.left.map(e => Error(e).prepended(value)))
    def *:[C](value: => C)(using Functor[F], Show[C]): F[Either[Error, B]] = fe.map(_.left.map(e => Error(e).prepended(value)))
    def to(error: => Error)(using Functor[F]): F[Either[Error, B]] = fe.map(_.left.map(e => Error(e).to(error)))
  end extension
end EitherFSyntax
object EitherFSyntax extends EitherFSyntax
