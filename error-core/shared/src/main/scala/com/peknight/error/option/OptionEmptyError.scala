package com.peknight.error.option

import com.peknight.error.std.EmptyError

object OptionEmptyError:
  def apply(label: String, message: String): OptionEmptyError =
    EmptyError(OptionEmpty, label, message)

  def apply(label: String)(using OptionEmptyErrorShow): OptionEmptyError =
    EmptyError(OptionEmpty, label)
end OptionEmptyError


