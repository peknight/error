package com.peknight.error.std

import com.peknight.error.Error

import scala.reflect.ClassTag

trait WrongClassTag[A] extends WrongType:
  def expectedClassTag: ClassTag[A]
  def expectedType: String = Error.errorClass(expectedClassTag.runtimeClass)
end WrongClassTag
object WrongClassTag:
  private[this] case class WrongClassTag[A](expectedClassTag: ClassTag[A])
    extends com.peknight.error.std.WrongClassTag[A]
  def apply[A](using classTag: ClassTag[A]): com.peknight.error.std.WrongClassTag[A] = WrongClassTag[A](classTag)
end WrongClassTag
