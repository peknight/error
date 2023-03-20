package com.peknight.error.spire

import com.peknight.error.Error.StandardError
import com.peknight.error.ErrorShow
import com.peknight.error.std.{EmptyError, EmptyErrorShow, EmptyErrorT}
import spire.math.Interval

package object math:
  type IntervalEmptyErrorT[Ext] = EmptyErrorT[IntervalEmpty, Ext]
  type IntervalEmptyError = EmptyError[IntervalEmpty]
  type IntervalEmptyErrorShow = EmptyErrorShow[IntervalEmpty]
  type IntervalNotContainsErrorT[N, Ext] = StandardError[IntervalNotContains, N, Interval[N], Ext]
  type IntervalNotContainsError[N] = StandardError[IntervalNotContains, N, Interval[N], Unit]
  type IntervalNotContainsErrorShow[N] = ErrorShow[IntervalNotContains, N, Interval[N]]
end math
