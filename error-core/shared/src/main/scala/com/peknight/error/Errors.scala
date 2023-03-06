package com.peknight.error

import cats.data.NonEmptyList

sealed trait Errors extends Error:
  def errors: NonEmptyList[Error]
  def message: String = errors.map(_.message).toList.mkString("\n")
end Errors

object Errors:
  def apply(errors0: NonEmptyList[Error]): Errors = new Errors:
    def errors: NonEmptyList[Error] = errors0

  def apply(head: Error, tail: List[Error]): Errors = apply(NonEmptyList(head, tail))

  def one(head: Error): Errors = apply(NonEmptyList.one(head))

  def of(head: Error, tail: Error*): Errors = apply(NonEmptyList.of(head, tail*))

  def unapply(errors: Errors): Some[NonEmptyList[Error]] = Some(errors.errors)
end Errors
