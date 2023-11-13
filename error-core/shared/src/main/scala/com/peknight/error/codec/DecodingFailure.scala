package com.peknight.error.codec

import com.peknight.error.Error

trait DecodingFailure extends Error:
  override def lowPriorityLabelMessage(label: String): Option[String] = Some(s"decoding $label failed")
end DecodingFailure
object DecodingFailure extends DecodingFailure
