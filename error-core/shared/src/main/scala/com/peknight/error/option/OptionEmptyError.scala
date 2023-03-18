package com.peknight.error.option

import com.peknight.error.std.EmptyError

object OptionEmptyError:
  def apply(label: String): OptionEmptyError = EmptyError(OptionEmpty, label)
end OptionEmptyError


