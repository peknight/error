package com.peknight.error.std

import com.peknight.error.Error.StandardError

object EmptyError:
  def apply[E <: Empty](errorType: E, label: String, message: String): EmptyError[E] =
    StandardError(errorType, label, (), (), message)

  def apply[E <: Empty](errorType: E, label: String)(using errorShow: EmptyErrorShow[E]): EmptyError[E] =
    StandardError(errorType, label, (), (), errorShow.show(errorType, label, (), ()))
end EmptyError
