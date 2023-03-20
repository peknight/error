package com.peknight.error.spire.math.interval

import com.peknight.error.Error.{NoError, StandardError}

object UnboundError:
  def apply[Ext](label: String, ext: Ext, message: String): UnboundErrorT[Ext] =
    StandardError(Unbound, label, (), (), ext, message, NoError)

  def apply(label: String, message: String): UnboundError =
    StandardError(Unbound, label, (), (), (), message, NoError)

  def apply(label: String)(using errorShow: UnboundErrorShow): UnboundError =
    StandardError(Unbound, label, (), (), (), errorShow.show(Unbound, label, (), ()), NoError)
end UnboundError
