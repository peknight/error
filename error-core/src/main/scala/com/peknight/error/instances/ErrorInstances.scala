package com.peknight.error.instances

import cats.Monoid
import cats.data.NonEmptyList
import com.peknight.error.Error.{Errors, pure}
import com.peknight.error.{Error, Success}

trait ErrorInstances:
  given Monoid[Error] with
    def empty: Error = Success
    def combine(x: Error, y: Error): Error = (pure(x), pure(y)) match
      case (Success, yError) => yError
      case (xError, Success) => xError
      case (Errors(xErrors), Errors(yErrors)) => Errors(xErrors ++ yErrors.toList)
      case (Errors(xErrors), yError) => Errors(NonEmptyList(xErrors.head, xErrors.tail :+ yError))
      case (xError, Errors(yErrors)) => Errors(NonEmptyList(xError, yErrors.toList))
      case (xError, yError) => Errors(NonEmptyList(xError, List(yError)))
  end given
end ErrorInstances
