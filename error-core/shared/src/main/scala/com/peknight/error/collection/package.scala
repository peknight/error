package com.peknight.error

import com.peknight.error.std.{EmptyError, EmptyErrorShow}

package object collection:
  type CollectionEmptyError = EmptyError[CollectionEmpty]
  type CollectionEmptyErrorShow = EmptyErrorShow[CollectionEmpty]
end collection
