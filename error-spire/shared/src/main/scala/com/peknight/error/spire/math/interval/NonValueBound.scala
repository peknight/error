package com.peknight.error.spire.math.interval

import com.peknight.error.Error

trait NonValueBound extends Error:
  override def lowPriorityLabelMessage(label: String): Option[String] = Some(s"$label is not value bound")
end NonValueBound
object NonValueBound extends NonValueBound
