package com.peknight.error.collection

trait ElementInconsistent extends CollectionError:
  override protected def lowPriorityMessage: Option[String] = Some("Element inconsistent")
end ElementInconsistent
object ElementInconsistent extends ElementInconsistent
