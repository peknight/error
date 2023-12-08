package com.peknight.error.std

import com.peknight.error.Error

trait WrongClass[A] extends WrongType:
  def expectedClass: Class[A]
  def expectedType: String = Error.errorClass(expectedClass)
end WrongClass
object WrongClass:
  private[this] case class WrongClass[A](expectedClass: Class[A]) extends com.peknight.error.std.WrongClass[A]
  def apply[A](expectedClass: Class[A]): com.peknight.error.std.WrongClass[A] = WrongClass[A](expectedClass)
end WrongClass
