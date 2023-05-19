package com.peknight.error

import com.peknight.error.Error.StandardError
import com.peknight.error.ErrorShow

package object std:
  type Error = StandardError[_, _, _, _]
  type EmptyError[E <: Empty] = StandardError[E, Unit, Unit, EmptyTuple]
  type EmptyErrorShow[E <: Empty] = ErrorShow[E, Unit, Unit]
  type UndefinedError = StandardError[Undefined, Unit, Unit, EmptyTuple]
  type UndefinedErrorShow = ErrorShow[Undefined, Unit, Unit]
  type JavaThrowableError[T <: Throwable] = StandardError[JavaThrowable[T], T, Unit, EmptyTuple]
  type JavaThrowableErrorShow[T <: Throwable] = ErrorShow[JavaThrowable[T], T, Unit]
end std
