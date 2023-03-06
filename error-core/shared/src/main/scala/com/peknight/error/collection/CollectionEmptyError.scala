package com.peknight.error.collection

import com.peknight.error.EmptyError

trait CollectionEmptyError extends EmptyError

object CollectionEmptyError:
  def apply(label0: String): CollectionEmptyError = new CollectionEmptyError:
    def label: String = label0

  def unapply(error: CollectionEmptyError): Some[String] = Some(error.label)

end CollectionEmptyError
