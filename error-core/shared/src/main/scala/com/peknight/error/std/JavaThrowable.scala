package com.peknight.error.std

import com.peknight.error.Error
trait JavaThrowable[T <: Throwable] extends Error:
  def throwable: T
  override def lowPriorityMessage: Option[String] = Option(throwable.getMessage).filter(_.nonEmpty)
end JavaThrowable
object JavaThrowable:
  private case class JavaThrowable[T <: Throwable](throwable: T) extends com.peknight.error.std.JavaThrowable[T]
  def apply[T <: Throwable](throwable: T): com.peknight.error.std.JavaThrowable[T] = JavaThrowable(throwable)
end JavaThrowable
