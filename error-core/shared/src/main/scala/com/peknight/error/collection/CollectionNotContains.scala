package com.peknight.error.collection

import cats.Show
import cats.syntax.show.*
import com.peknight.error.{Error, collection}

trait CollectionNotContains[A, C <: IterableOnce[A]] extends Error:
  def value: A
  def collection: C
end CollectionNotContains
object CollectionNotContains:
  private case class CollectionNotContains[A, C <: IterableOnce[A]](value: A, collection: C)(using Show[A])
    extends com.peknight.error.collection.CollectionNotContains[A, C]:
    override protected def lowPriorityMessage: Option[String] =
      Some(s"${value.show} is not in [${collection.iterator.map(_.show).mkString(", ")}]")
    override protected def lowPriorityLabelMessage(label: String): Option[String] =
      Some(s"$label(${value.show}) is not in [${collection.iterator.map(_.show).mkString(", ")}]")
  end CollectionNotContains
  def apply[A: Show, C <: IterableOnce[A]](value: A, collection: C)
  : com.peknight.error.collection.CollectionNotContains[A, C] =
    CollectionNotContains(value, collection)
end CollectionNotContains
