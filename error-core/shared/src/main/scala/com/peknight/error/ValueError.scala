package com.peknight.error

trait ValueError[A] extends LabelledError:
  def value: A
