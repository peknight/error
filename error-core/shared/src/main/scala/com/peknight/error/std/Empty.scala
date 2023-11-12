package com.peknight.error.std

import com.peknight.error.Error

trait Empty extends Error:
  override def labelMessage(label: String): Option[String] = Some(s"$label is empty")
end Empty
object Empty extends Empty
