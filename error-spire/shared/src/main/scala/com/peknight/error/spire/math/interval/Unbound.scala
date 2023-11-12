package com.peknight.error.spire.math.interval

import com.peknight.error.Error

trait Unbound extends Error:
  override def labelMessage(label: String): Option[String] = Some(s"$label is unbound")
end Unbound
object Unbound extends Unbound
