package com.peknight.error.spire.math

import com.peknight.error.Error.StandardError
import com.peknight.error.ErrorShow
import com.peknight.error.std.{EmptyError, EmptyErrorShow, EmptyErrorT}

package object interval:
  type BoundEmptyErrorT[Ext] = EmptyErrorT[BoundEmpty, Ext]
  type BoundEmptyError = EmptyError[BoundEmpty]
  type BoundEmptyErrorShow = EmptyErrorShow[BoundEmpty]
  type UnboundErrorT[Ext] = StandardError[Unbound, Unit, Unit, Ext]
  type UnboundError = StandardError[Unbound, Unit, Unit, Unit]
  type UnboundErrorShow = ErrorShow[Unbound, Unit, Unit]
end interval
