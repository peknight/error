package com.peknight.error

import com.peknight.error.std.EmptyError

package object option:
  type OptionEmptyError = EmptyError[OptionEmpty]
end option
