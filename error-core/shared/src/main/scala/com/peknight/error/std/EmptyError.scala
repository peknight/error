package com.peknight.error.std

import com.peknight.error.labelled.LabelledValueError
import com.peknight.generic.tuple.syntax.label

object EmptyError:
  def apply[T <: Empty](errorType: T, label: String): EmptyError[T] =
    LabelledValueError(errorType, ().label(label))((_, _) => s"$label is empty")
end EmptyError
