package com.peknight.error.std

import com.peknight.error.platform.LabelledErrorPlatform

trait UndefinedPlatform extends LabelledErrorPlatform:
  def labelToMessage(label: String): String = s"$label is undefined"
end UndefinedPlatform
