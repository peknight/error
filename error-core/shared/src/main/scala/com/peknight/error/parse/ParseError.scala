package com.peknight.error.parse

import com.peknight.error.Error.StandardError

object ParseError:
  def apply[E <: Parse, Actual](errorType: E, label: String, actual: Actual, message: String)
  : ParseError[E, Actual] =
    StandardError(errorType, label, actual, (), message)

  def apply[E <: Parse, Actual](errorType: E, label: String, actual: Actual)
                               (using errorShow: ParseErrorShow[E, Actual]): ParseError[E, Actual] =
    StandardError(errorType, label, actual, (), errorShow.show(errorType, label, actual, ()))
end ParseError
