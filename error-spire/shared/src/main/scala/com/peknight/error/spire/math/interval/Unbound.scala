package com.peknight.error.spire.math.interval

import com.peknight.error.ErrorType

trait Unbound extends ErrorType
case object Unbound extends Unbound:
  given UnboundErrorShow with
    def show(errorType: Unbound, label: String, actual: Unit, expect: Unit): String = s"$label is unbound"
  end given
end Unbound
