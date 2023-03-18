package com.peknight.error.spire.math

import com.peknight.error.std.EmptyError

object IntervalEmptyError:
  def apply(label: String): IntervalEmptyError = EmptyError(IntervalEmpty, label)
end IntervalEmptyError
