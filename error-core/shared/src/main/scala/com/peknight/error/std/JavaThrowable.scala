package com.peknight.error.std

import com.peknight.error.ErrorType

trait JavaThrowable[T <: Throwable] extends ErrorType
object JavaThrowable extends JavaThrowable[Throwable]:
  def apply[T <: Throwable]: JavaThrowable[T] = new JavaThrowable[T]{}
end JavaThrowable
