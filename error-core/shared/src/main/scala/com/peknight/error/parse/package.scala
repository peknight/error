package com.peknight.error

import com.peknight.error.Error.StandardError

package object parse:
  type ParseError[E <: ParseFailed, Actual] = StandardError[E, Actual, Unit, EmptyTuple]
  type ParseErrorShow[E <: ParseFailed, Actual] = ErrorShow[E, Actual, Unit]
end parse
