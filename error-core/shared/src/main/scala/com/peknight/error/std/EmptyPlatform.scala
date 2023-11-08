package com.peknight.error.std

import com.peknight.error.platform.LabelledErrorPlatform

trait EmptyPlatform extends LabelledErrorPlatform:
  def labelToMessage(label: String): String = s"$label is empty"
end EmptyPlatform
