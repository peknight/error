package com.peknight.error.spire.math

import com.peknight.error.Error.StandardError
import com.peknight.error.ErrorShow
import com.peknight.error.std.{EmptyError, EmptyErrorShow}

package object interval:
  type BoundEmptyError = EmptyError[BoundEmpty]
  type BoundEmptyErrorShow = EmptyErrorShow[BoundEmpty]
  type UnboundError = StandardError[Unbound, Unit, Unit, EmptyTuple]
  type UnboundErrorShow = ErrorShow[Unbound, Unit, Unit]
end interval
