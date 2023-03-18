package com.peknight.error.std

import com.peknight.error.labelled.LabelledValueError
import com.peknight.generic.tuple.syntax.label

object UndefinedError:
  def apply(label: String): UndefinedError =
    LabelledValueError(Undefined, ().label(label))((_, _) => s"$label is undefined")
  end apply
end UndefinedError


