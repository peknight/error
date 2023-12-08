package com.peknight.error.parse

import com.peknight.error.Error

trait ParsingFailure extends Error:
  override protected def lowPriorityLabelMessage(label: String): Option[String] = Some(s"parse $label failed")
end ParsingFailure
object ParsingFailure extends ParsingFailure
