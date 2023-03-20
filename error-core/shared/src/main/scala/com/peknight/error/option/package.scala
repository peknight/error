package com.peknight.error

import com.peknight.error.std.{EmptyError, EmptyErrorShow, EmptyErrorT}

package object option:
  type OptionEmptyErrorT[Ext] = EmptyErrorT[OptionEmpty, Ext]
  type OptionEmptyError = EmptyError[OptionEmpty]
  type OptionEmptyErrorShow = EmptyErrorShow[OptionEmpty]
end option
