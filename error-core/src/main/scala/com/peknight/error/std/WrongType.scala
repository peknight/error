package com.peknight.error.std

import com.peknight.error.Error

trait WrongType extends Error:
  def expectedType: String
  def actualType: Option[String] = None
  override protected def lowPriorityLabelMessage(label: String): Option[String] =
    Some(s"Got $label with wrong type${actualType.fold("")(actual => s": $actual")}, expecting $expectedType")
  override protected def lowPriorityMessage: Option[String] = Some(s"Wrong type${actualType.fold("")(actual => s": $actual")}, expecting $expectedType")
end WrongType
object WrongType:
  private case class WrongType(expectedType: String, override val actualType: Option[String])
    extends com.peknight.error.std.WrongType
  def apply(expectedType: String, actualType: Option[String] = None): com.peknight.error.std.WrongType =
    WrongType(expectedType, actualType)
end WrongType

