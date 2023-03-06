package com.peknight.error.spire.math.interval

import com.peknight.error.LabelledError

trait UnboundError extends LabelledError:
  def message: String = s"$label is unbound"
end UnboundError

object UnboundError:
  def apply(label0: String): UnboundError = new UnboundError:
    def label: String = label0

  def unapply(error: UnboundError): Some[String] = Some(error.label)

end UnboundError

