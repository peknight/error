package com.peknight.error.std

import com.peknight.error.Lift

trait JavaThrowable[+T <: Throwable] extends Lift[T]:
  override def lowPriorityMessage: Option[String] = Option(error.getMessage).filter(_.nonEmpty)
end JavaThrowable
object JavaThrowable:
  private case class JavaThrowable[+T <: Throwable](error: T) extends com.peknight.error.std.JavaThrowable[T]
  def apply[T <: Throwable](error: T): com.peknight.error.std.JavaThrowable[T] = JavaThrowable(error)
end JavaThrowable
