package com.peknight.error.spire

import com.peknight.error.labelled.LabelledValueError
import com.peknight.error.std.EmptyError
import spire.math.Interval

package object math:
  type IntervalEmptyError = EmptyError[IntervalEmpty]
  type IntervalNotContainsError[N] = LabelledValueError[IntervalNotContains, (N, Interval[N])]
end math
