package com.peknight.error

import com.peknight.error.Error.StandardError
import com.peknight.error.ErrorShow

package object std:
  type EmptyErrorT[E <: Empty, Ext] = StandardError[E, Unit, Unit, Ext]
  type EmptyError[E <: Empty] = StandardError[E, Unit, Unit, Unit]
  type EmptyErrorShow[E <: Empty] = ErrorShow[E, Unit, Unit]
  type UndefinedErrorT[Ext] = StandardError[Undefined, Unit, Unit, Ext]
  type UndefinedError = StandardError[Undefined, Unit, Unit, Unit]
  type UndefinedErrorShow = ErrorShow[Undefined, Unit, Unit]
end std
