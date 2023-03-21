package com.peknight.error.collection

import com.peknight.error.std.EmptyError

object CollectionEmptyError:
  def apply(label: String, message: String): CollectionEmptyError =
    EmptyError(CollectionEmpty, label, message)

  def apply(label: String)(using CollectionEmptyErrorShow): CollectionEmptyError =
    EmptyError(CollectionEmpty, label)
end CollectionEmptyError


