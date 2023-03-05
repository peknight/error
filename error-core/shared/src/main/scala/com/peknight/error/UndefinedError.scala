package com.peknight.error

case class UndefinedError(label: String) extends LabelledError:
  def message: String = s"$label is undefined"
