package com.peknight.error

import com.peknight.error.std.{EmptyError, EmptyErrorShow}

package object option:
  type OptionEmptyError = EmptyError[OptionEmpty]
  type OptionEmptyErrorShow = EmptyErrorShow[OptionEmpty]
end option
