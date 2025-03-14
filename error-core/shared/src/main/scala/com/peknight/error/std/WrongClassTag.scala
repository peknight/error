package com.peknight.error.std

import com.peknight.error.Error

import scala.reflect.ClassTag

trait WrongClassTag[E] extends WrongType:
  def expectedClassTag: ClassTag[E]
  def expectedType: String = Error.showClassTag(using expectedClassTag)
end WrongClassTag
object WrongClassTag:
  private case class WrongClassTag[E](expectedClassTag: ClassTag[E], override val actualType: Option[String])
    extends com.peknight.error.std.WrongClassTag[E]
  def apply[E](using classTag: ClassTag[E]): com.peknight.error.std.WrongClassTag[E] = WrongClassTag[E](classTag, None)
  def apply[E](a: Any)(using classTag: ClassTag[E]): com.peknight.error.std.WrongClassTag[E] = WrongClassTag[E](classTag, Some(Error.showType(a)))
end WrongClassTag
