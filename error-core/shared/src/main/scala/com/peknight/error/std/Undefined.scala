package com.peknight.error.std

import com.peknight.error.Error

trait Undefined extends Error:
  override def labelMessage(label: String): Option[String] = Some(s"$label is undefined")
end Undefined
object Undefined extends Undefined
