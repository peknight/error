package com.peknight.error.syntax

import cats.data.{EitherT, Ior, IorT}
import cats.syntax.applicativeError.*
import cats.syntax.functor.*
import cats.{ApplicativeError, Show}
import com.peknight.error.Error

trait ApplicativeErrorSyntax:
  extension [F[_], A] (fa: F[A])
    def asError(using ApplicativeError[F, Throwable]): F[Either[Error, A]] =
      fa.attempt.map(_.left.map(Error.apply))
    def iorError(using ApplicativeError[F, Throwable]): F[Ior[Error, A]] =
      fa.attempt.map(_.fold(e => Ior.left(Error(e)), Ior.right))
    def faeLiftET(using ApplicativeError[F, Throwable]): EitherT[F, Error, A] =
      EitherT(fa.attempt.map(_.left.map(Error.apply)))
    def faeLiftIT(using ApplicativeError[F, Throwable]): IorT[F, Error, A] =
      IorT(fa.attempt.map(_.fold(e => Ior.left(Error(e)), Ior.right)))
    def label(label: => String)(using ApplicativeError[F, Throwable]): F[Either[Error, A]] =
      fa.attempt.map(_.left.map(e => Error(e).label(label)))
    def prependLabel(label: => String)(using ApplicativeError[F, Throwable]): F[Either[Error, A]] =
      fa.attempt.map(_.left.map(e => Error(e).prependLabel(label)))
    def message(message: => String)(using ApplicativeError[F, Throwable]): F[Either[Error, A]] =
      fa.attempt.map(_.left.map(e => Error(e).message(message)))
    def value[B](value: => B)(using ApplicativeError[F, Throwable], Show[B]): F[Either[Error, A]] =
      fa.attempt.map(_.left.map(e => Error(e).value(value)))
    def prepended[B](value: => B)(using ApplicativeError[F, Throwable], Show[B]): F[Either[Error, A]] =
      fa.attempt.map(_.left.map(e => Error(e).prepended(value)))
    def *:[B](value: => B)(using ApplicativeError[F, Throwable], Show[B]): F[Either[Error, A]] =
      fa.attempt.map(_.left.map(e => Error(e).prepended(value)))
    def to(error: => Error)(using ApplicativeError[F, Throwable]): F[Either[Error, A]] =
      fa.attempt.map(_.left.map(e => Error(e).to(error)))
  end extension

  extension [F[_], E, A] (fa: F[Either[E, A]])
    def aeAsError(using ApplicativeError[F, Throwable]): F[Either[Error, A]] =
      fa.attempt.map(_.flatten.left.map(Error.apply))
    def aeLiftET(using ApplicativeError[F, Throwable]): EitherT[F, Error, A] =
      EitherT(fa.attempt.map(_.flatten.left.map(Error.apply)))
  end extension

end ApplicativeErrorSyntax
object ApplicativeErrorSyntax extends ApplicativeErrorSyntax
