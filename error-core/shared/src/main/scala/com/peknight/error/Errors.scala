package com.peknight.error

import cats.data.NonEmptyList

trait Errors[+E] extends Error:
  def errors: NonEmptyList[E]
  override def messages: List[String] = errors.toList.flatMap {
    case e: Error => e.messages
    case e => List(Error.pureMessage(e))
  }
  override protected def lowPriorityMessage: Option[String] = Some(messages.mkString(", "))
end Errors
