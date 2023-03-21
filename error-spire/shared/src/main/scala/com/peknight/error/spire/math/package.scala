package com.peknight.error.spire

import com.peknight.error.Error.StandardError
import com.peknight.error.ErrorShow
import com.peknight.error.std.{EmptyError, EmptyErrorShow}
import spire.math.Interval

package object math:
  type IntervalEmptyError = EmptyError[IntervalEmpty]
  type IntervalEmptyErrorShow = EmptyErrorShow[IntervalEmpty]
  type IntervalNotContainsError[N] = StandardError[IntervalNotContains, N, Interval[N], EmptyTuple]
  type IntervalNotContainsErrorShow[N] = ErrorShow[IntervalNotContains, N, Interval[N]]
end math
