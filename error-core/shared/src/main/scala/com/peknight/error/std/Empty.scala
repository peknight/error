package com.peknight.error.std

import com.peknight.error.Error

trait Empty extends Error:
  override protected def lowPriorityLabelMessage(label: String): Option[String] = Some(s"$label is empty")
end Empty
object Empty extends Empty
