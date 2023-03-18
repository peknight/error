package com.peknight.error.spire.math.interval

import com.peknight.error.labelled.LabelledValueError
import com.peknight.error.std.EmptyError

object BoundEmptyError:
  def apply(label: String): BoundEmptyError = EmptyError(BoundEmpty, label)
end BoundEmptyError
