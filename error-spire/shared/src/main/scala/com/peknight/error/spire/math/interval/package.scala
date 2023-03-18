package com.peknight.error.spire.math

import com.peknight.error.labelled.LabelledValueError
import com.peknight.error.std.EmptyError

package object interval:
  type BoundEmptyError = EmptyError[BoundEmpty]
  type UnboundError = LabelledValueError[Unbound, Unit]
end interval
