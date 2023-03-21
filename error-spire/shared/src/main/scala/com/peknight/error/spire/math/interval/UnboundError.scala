package com.peknight.error.spire.math.interval

import com.peknight.error.Error.StandardError

object UnboundError:
  def apply(label: String, message: String): UnboundError =
    StandardError(Unbound, label, (), (), message)

  def apply(label: String)(using errorShow: UnboundErrorShow): UnboundError =
    StandardError(Unbound, label, (), (), errorShow.show(Unbound, label, (), ()))
end UnboundError
