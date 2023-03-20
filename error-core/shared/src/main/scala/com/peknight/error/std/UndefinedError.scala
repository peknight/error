package com.peknight.error.std

import com.peknight.error.Error.{NoError, StandardError}

object UndefinedError:
  def apply[Ext](label: String, ext: Ext, message: String): UndefinedErrorT[Ext] =
    StandardError(Undefined, label, (), (), ext, message, NoError)

  def apply(label: String, message: String): UndefinedError =
    StandardError(Undefined, label, (), (), (), message, NoError)

  def apply(label: String)(using errorShow: UndefinedErrorShow): UndefinedError =
    StandardError(Undefined, label, (), (), (), errorShow.show(Undefined, label, (), ()), NoError)
end UndefinedError


