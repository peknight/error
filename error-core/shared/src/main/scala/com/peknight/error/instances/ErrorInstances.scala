package com.peknight.error.instances

import cats.Monoid
import com.peknight.error.Error
import com.peknight.error.Error.{Errors, Success, pure}

trait ErrorInstances:
  given Monoid[Error] with
    def empty: Error = Success
    def combine(x: Error, y: Error): Error = (pure(x), pure(y)) match
      case (Success, yError) => yError
      case (xError, Success) => xError
      case (Errors(xErrors), Errors(yErrors)) => Errors(xErrors ++ yErrors.toList)
      case (Errors(xErrors), yError) => Errors(xErrors.head, xErrors.tail :+ yError)
      case (xError, Errors(yErrors)) => Errors(xError, yErrors.toList)
      case (xError, yError) => Errors(xError, yError)
  end given
end ErrorInstances
