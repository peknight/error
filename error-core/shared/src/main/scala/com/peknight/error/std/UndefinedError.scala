package com.peknight.error.std

import com.peknight.error.Error.StandardError

object UndefinedError:
  def apply(label: String, message: String): UndefinedError =
    StandardError(Undefined, label, (), (), message)

  def apply(label: String)(using errorShow: UndefinedErrorShow): UndefinedError =
    StandardError(Undefined, label, (), (), errorShow.show(Undefined, label, (), ()))
end UndefinedError
