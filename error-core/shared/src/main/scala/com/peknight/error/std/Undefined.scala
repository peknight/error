package com.peknight.error.std

import com.peknight.error.ErrorType

trait Undefined extends ErrorType
case object Undefined extends Undefined:
  given UndefinedErrorShow with
    def show(errorType: Undefined, label: String, actual: Unit, expect: Unit): String = s"$label is undefined"
  end given
end Undefined
