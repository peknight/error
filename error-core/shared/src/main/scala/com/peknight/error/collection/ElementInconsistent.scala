package com.peknight.error.collection

import com.peknight.error.Error

trait ElementInconsistent extends Error:
  override protected def lowPriorityMessage: Option[String] = Some("Element inconsistent")
end ElementInconsistent
object ElementInconsistent extends ElementInconsistent
