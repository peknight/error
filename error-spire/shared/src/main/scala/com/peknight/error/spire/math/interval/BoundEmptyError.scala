package com.peknight.error.spire.math.interval

import com.peknight.error.std.EmptyError

object BoundEmptyError:
  def apply(label: String, message: String): BoundEmptyError =
    EmptyError(BoundEmpty, label, message)

  def apply(label: String)(using BoundEmptyErrorShow): BoundEmptyError =
    EmptyError(BoundEmpty, label)
end BoundEmptyError
