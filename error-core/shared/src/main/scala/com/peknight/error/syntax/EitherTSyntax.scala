package com.peknight.error.syntax

import cats.Functor
import cats.syntax.functor.*
import com.peknight.error.Error

trait EitherTSyntax:
  extension [F[_], A, B] (fe: F[Either[A, B]])
    def asError(using Functor[F]): F[Either[Error, B]] = fe.map(_.left.map(Error.apply))
    def label(label: => String)(using Functor[F]): F[Either[Error, B]] = fe.map(_.left.map(e => Error(e).label(label)))
    def prependLabel(label: => String)(using Functor[F]): F[Either[Error, B]] =
      fe.map(_.left.map(e => Error(e).prependLabel(label)))
    def message(message: => String)(using Functor[F]): F[Either[Error, B]] =
      fe.map(_.left.map(e => Error(e).message(message)))
    def value[T](value: => T)(using Functor[F]): F[Either[Error, B]] = fe.map(_.left.map(e => Error(e).value(value)))
    def prepended[T](value: => T)(using Functor[F]): F[Either[Error, B]] =
      fe.map(_.left.map(e => Error(e).prepended(value)))
    def *:[T](value: => T)(using Functor[F]): F[Either[Error, B]] = fe.map(_.left.map(e => Error(e).prepended(value)))
    def to(error: => Error)(using Functor[F]): F[Either[Error, B]] = fe.map(_.left.map(e => Error(e).to(error)))
  end extension
end EitherTSyntax
object EitherTSyntax extends EitherTSyntax
