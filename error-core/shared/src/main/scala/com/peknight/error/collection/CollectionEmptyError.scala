package com.peknight.error.collection

import com.peknight.error.std.EmptyError

object CollectionEmptyError:
  def apply(label: String): CollectionEmptyError = EmptyError(CollectionEmpty, label)
end CollectionEmptyError


