package com.peknight.error.std

import com.peknight.error.Error

trait WrongType extends Error:
  def expectedType: String
  override protected def lowPriorityLabelMessage(label: String): Option[String] =
    Some(s"Got $label with wrong type, expecting $expectedType")
  override protected def lowPriorityMessage: Option[String] = Some(s"Wrong type, expecting $expectedType")
end WrongType
object WrongType:
  private[this] case class WrongType(expectedType: String) extends com.peknight.error.std.WrongType
  def apply(expectedType: String): com.peknight.error.std.WrongType = WrongType(expectedType)
end WrongType

