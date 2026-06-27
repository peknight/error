package com.peknight.error.collection

import cats.Show
import cats.syntax.show.*

trait CollectionNoInteraction[A, C1 <: IterableOnce[A], C2 <: IterableOnce[A]] extends CollectionError:
  def actual: C1
  def expected: C2
end CollectionNoInteraction
object CollectionNoInteraction:
  private case class CollectionNoInteraction[A: Show, C1 <: IterableOnce[A], C2 <: IterableOnce[A]](actual: C1, expected: C2)
    extends com.peknight.error.collection.CollectionNoInteraction[A, C1, C2]:
    override protected def lowPriorityMessage: Option[String] =
      Some(s"[${actual.iterator.map(_.show).mkString(", ")}] has no interaction with [${expected.iterator.map(_.show).mkString(", ")}]")
    override protected def lowPriorityLabelMessage(label: String): Option[String] =
      Some(s"$label([${actual.iterator.map(_.show).mkString(", ")}]) has no interaction with [${expected.iterator.map(_.show).mkString(", ")}]")
  end CollectionNoInteraction
  def apply[A: Show, C1 <: IterableOnce[A], C2 <: IterableOnce[A]](actual: C1, expected: C2)
  : com.peknight.error.collection.CollectionNoInteraction[A, C1, C2] =
    CollectionNoInteraction(actual, expected)
end CollectionNoInteraction
