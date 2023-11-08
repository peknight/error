package com.peknight.error.parse

import com.peknight.error.platform.LabelledErrorPlatform

trait ParseFailurePlatform extends LabelledErrorPlatform:
  def labelToMessage(label: String): String = s"parse $label failed"
end ParseFailurePlatform
