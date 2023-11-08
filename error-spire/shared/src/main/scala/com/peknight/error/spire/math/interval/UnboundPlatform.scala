package com.peknight.error.spire.math.interval

import com.peknight.error.platform.LabelledErrorPlatform

trait UnboundPlatform extends LabelledErrorPlatform:
  def labelToMessage(label: String): String = s"$label is unbound"
end UnboundPlatform
