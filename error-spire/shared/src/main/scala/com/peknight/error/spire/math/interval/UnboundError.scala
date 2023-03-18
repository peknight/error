package com.peknight.error.spire.math.interval

import com.peknight.error.labelled.LabelledValueError
import com.peknight.generic.tuple.syntax.label

object UnboundError:
  def apply(label: String): UnboundError =
    LabelledValueError(Unbound, ().label(label))((_, _) => s"$label is unbound")
  end apply
end UnboundError
