package com.peknight.error.spire.math

import com.peknight.error.Error.StandardError
import spire.math.Interval

object IntervalNotContainsError:
  def apply[N](label: String, actual: N, expect: Interval[N], message: String): IntervalNotContainsError[N] =
    StandardError(IntervalNotContains, label, actual, expect, message)

  def apply[N](label: String, actual: N, expect: Interval[N])(using errorShow: IntervalNotContainsErrorShow[N])
  : IntervalNotContainsError[N] =
    StandardError(IntervalNotContains, label, actual, expect, errorShow.show(IntervalNotContains, label, actual, expect))
end IntervalNotContainsError
