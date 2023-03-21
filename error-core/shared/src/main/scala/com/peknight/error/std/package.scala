package com.peknight.error

import com.peknight.error.Error.StandardError
import com.peknight.error.ErrorShow

package object std:
  type EmptyError[E <: Empty] = StandardError[E, Unit, Unit, EmptyTuple]
  type EmptyErrorShow[E <: Empty] = ErrorShow[E, Unit, Unit]
  type UndefinedError = StandardError[Undefined, Unit, Unit, EmptyTuple]
  type UndefinedErrorShow = ErrorShow[Undefined, Unit, Unit]
end std
