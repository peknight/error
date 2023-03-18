package com.peknight.error

import com.peknight.error.std.EmptyError

package object collection:
  type CollectionEmptyError = EmptyError[CollectionEmpty]
end collection
