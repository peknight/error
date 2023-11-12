package com.peknight.error.parse

import com.peknight.error.Error

trait ParseFailure extends Error:
  override def labelMessage(label: String): Option[String] = Some(s"parse $label failed")
end ParseFailure
object ParseFailure extends ParseFailure
