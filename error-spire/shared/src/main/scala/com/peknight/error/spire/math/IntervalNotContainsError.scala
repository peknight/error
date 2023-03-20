package com.peknight.error.spire.math

import com.peknight.error.Error.{NoError, StandardError}
import spire.math.Interval

object IntervalNotContainsError:
  def apply[N, Ext](label: String, actual: N, expect: Interval[N], ext: Ext, message: String)
  : IntervalNotContainsErrorT[N, Ext] =
    StandardError(IntervalNotContains, label, actual, expect, ext, message, NoError)

  def apply[N](label: String, actual: N, expect: Interval[N], message: String)
  : IntervalNotContainsError[N] =
    StandardError(IntervalNotContains, label, actual, expect, (), message, NoError)

  def apply[N](label: String, actual: N, expect: Interval[N])
              (using errorShow: IntervalNotContainsErrorShow[N]): IntervalNotContainsError[N] =
    StandardError(IntervalNotContains, label, actual, expect, (),
      errorShow.show(IntervalNotContains, label, actual, expect), NoError)
end IntervalNotContainsError
