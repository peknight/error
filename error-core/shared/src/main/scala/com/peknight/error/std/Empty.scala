package com.peknight.error.std

import com.peknight.error.ErrorType

trait Empty extends ErrorType
case object Empty extends Empty:

  given [E <: Empty]: EmptyErrorShow[E] with
    def show(errorType: E, label: String, actual: Unit, expect: Unit): String = s"$label is empty"
  end given
end Empty
