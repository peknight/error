package com.peknight.error.std

import com.peknight.error.Error.StandardError

object JavaThrowableError:
  def apply[T <: Throwable](label: String, t: T, message: String): JavaThrowableError[T] =
    StandardError(JavaThrowable[T], label, t, (), message)

  def apply[T <: Throwable](label: String, t: T)(using errorShow: JavaThrowableErrorShow[T]): JavaThrowableError[T] =
    val errorType = JavaThrowable[T]
    StandardError(errorType, label, t, (), errorShow.show(errorType, label, t, ()))

  extension [T <: Throwable] (t: T)
    def messageToError(label: String): JavaThrowableError[T] = JavaThrowableError[T](label, t, t.getMessage)
    def toError(label: String, message: String): JavaThrowableError[T] =
      JavaThrowableError[T](label, t, message)
    def toError(label: String)(using JavaThrowableErrorShow[T]): JavaThrowableError[T] =
      JavaThrowableError[T](label, t)
  end extension
end JavaThrowableError
