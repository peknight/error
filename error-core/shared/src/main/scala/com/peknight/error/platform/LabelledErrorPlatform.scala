package com.peknight.error.platform

import com.peknight.error.Error

trait LabelledErrorPlatform extends Error:
  def labelToMessage(label: String): String
  def apply(label: String): Error = this.label(label).message(labelToMessage(label))
end LabelledErrorPlatform
