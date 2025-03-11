package com.peknight.error.collection

import cats.Show
import cats.syntax.show.*

trait MoreThenOneElement[A, C <: IterableOnce[A]] extends CollectionError:
  def collection: C
end MoreThenOneElement
object MoreThenOneElement:
  private case class MoreThenOneElement[A: Show, C <: IterableOnce[A]](collection: C)
    extends com.peknight.error.collection.MoreThenOneElement[A, C]:
    override protected def lowPriorityMessage: Option[String] =
      Some(s"[${collection.iterator.map(_.show).mkString(", ")}] has more then one element")
    override protected def lowPriorityLabelMessage(label: String): Option[String] =
      Some(s"$label([${collection.iterator.map(_.show).mkString(", ")}]) has more then one element")
  end MoreThenOneElement
  def apply[A: Show, C <: IterableOnce[A]](collection: C): com.peknight.error.collection.MoreThenOneElement[A, C] =
    MoreThenOneElement(collection)
end MoreThenOneElement
