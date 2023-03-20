package com.peknight.error

import com.peknight.error.std.{EmptyError, EmptyErrorShow, EmptyErrorT}

package object collection:
  type CollectionEmptyErrorT[Ext] = EmptyErrorT[CollectionEmpty, Ext]
  type CollectionEmptyError = EmptyError[CollectionEmpty]
  type CollectionEmptyErrorShow = EmptyErrorShow[CollectionEmpty]
end collection
