package com.peknight.error.std

import com.peknight.error.Error

trait WrongType extends Error:
  override protected def lowPriorityLabelMessage(label: String): Option[String] = Some(s"$label's type not match")
end WrongType
object WrongType extends WrongType

