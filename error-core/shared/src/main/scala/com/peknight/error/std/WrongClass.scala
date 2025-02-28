package com.peknight.error.std

import com.peknight.error.Error

trait WrongClass[E] extends WrongType:
  def expectedClass: Class[E]
  def expectedType: String = Error.showClass(expectedClass)
end WrongClass
object WrongClass:
  private case class WrongClass[E](expectedClass: Class[E], override val actualType: Option[String])
    extends com.peknight.error.std.WrongClass[E]
  def apply[E](expectedClass: Class[E]): com.peknight.error.std.WrongClass[E] = WrongClass[E](expectedClass, None)
  def apply[E](expectedClass: Class[E], actual: Any): com.peknight.error.std.WrongClass[E] =
    WrongClass[E](expectedClass, Some(Error.showType(actual)))
end WrongClass
