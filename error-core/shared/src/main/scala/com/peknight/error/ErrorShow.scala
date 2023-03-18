package com.peknight.error

trait ErrorShow[T <: ErrorType, V]:
  def show(errorType: T, value: V): String
end ErrorShow

object ErrorShow:
  def apply[T <: ErrorType, V](func: (T, V) => String): ErrorShow[T, V] = func(_, _)
end ErrorShow