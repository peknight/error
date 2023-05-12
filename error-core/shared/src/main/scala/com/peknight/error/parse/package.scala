package com.peknight.error

import com.peknight.error.Error.StandardError

package object parse:
  type ParseError[E <: Parse, Actual] = StandardError[E, Actual, Unit, EmptyTuple]
  type ParseErrorShow[E <: Parse, Actual] = ErrorShow[E, Actual, Unit]
end parse
