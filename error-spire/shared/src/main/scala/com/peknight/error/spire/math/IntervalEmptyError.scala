package com.peknight.error.spire.math

import com.peknight.error.std.EmptyError

object IntervalEmptyError:
  def apply(label: String, message: String): IntervalEmptyError =
    EmptyError(IntervalEmpty, label, message)

  def apply(label: String)(using IntervalEmptyErrorShow): IntervalEmptyError =
    EmptyError(IntervalEmpty, label)
end IntervalEmptyError
