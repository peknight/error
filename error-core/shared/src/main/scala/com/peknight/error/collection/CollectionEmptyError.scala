package com.peknight.error.collection

import com.peknight.error.std.EmptyError

object CollectionEmptyError:
  def apply[Ext](label: String, ext: Ext, message: String): CollectionEmptyErrorT[Ext] =
    EmptyError(CollectionEmpty, label, ext, message)

  def apply(label: String, message: String): CollectionEmptyError =
    EmptyError(CollectionEmpty, label, message)

  def apply(label: String)(using CollectionEmptyErrorShow): CollectionEmptyError =
    EmptyError(CollectionEmpty, label)
end CollectionEmptyError


