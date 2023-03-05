package com.peknight.error

trait EmptyError extends LabelledError:
  def message: String = s"$label is empty"