package com.peknight.error.std

import com.peknight.error.{Error, Lift}

trait JavaThrowable[+T <: Throwable] extends Lift[T]:
  override protected def lowPriorityMessage: Option[String] =
    Some(s"${Error.showType(error)}${Option(error.getMessage).filter(_.nonEmpty).fold("")(msg => s": $msg")}")
end JavaThrowable
object JavaThrowable:
  private case class JavaThrowable[+T <: Throwable](error: T) extends com.peknight.error.std.JavaThrowable[T]
  def apply[T <: Throwable](error: T): com.peknight.error.std.JavaThrowable[T] = JavaThrowable(error)
end JavaThrowable
