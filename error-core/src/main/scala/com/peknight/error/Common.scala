package com.peknight.error

import cats.{Eval, Show}

trait Common[+E, A] extends Lift[E]:
  def valueShow: Option[(A, Eval[String])]
  def value: Option[A] = valueShow.map(_._1)
  override def showValue: Option[String] = valueShow.map(_._2.value)
end Common
