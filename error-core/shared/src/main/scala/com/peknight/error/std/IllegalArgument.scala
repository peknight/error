package com.peknight.error.std

import com.peknight.error.Error

trait IllegalArgument[A] extends Error:
  def argument: A
  override protected def lowPriorityLabelMessage(label: String): Option[String] = Some(s"Illegal argument $label: $argument")
  override protected def lowPriorityMessage: Option[String] = Some(s"Illegal argument: $argument")
end IllegalArgument
object IllegalArgument:
  private[this] case class IllegalArgument[A](argument: A) extends com.peknight.error.std.IllegalArgument[A]
  def apply[A](argument: A): com.peknight.error.std.IllegalArgument[A] = IllegalArgument(argument)
end IllegalArgument
