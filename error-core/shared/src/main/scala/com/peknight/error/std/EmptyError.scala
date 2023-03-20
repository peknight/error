package com.peknight.error.std

import com.peknight.error.Error.{NoError, StandardError}

object EmptyError:
  def apply[E <: Empty, Ext](errorType: E, label: String, ext: Ext, message: String): EmptyErrorT[E, Ext] =
    StandardError(errorType, label, (), (), ext, message, NoError)

  def apply[E <: Empty](errorType: E, label: String, message: String): EmptyError[E] =
    StandardError(errorType, label, (), (), (), message, NoError)

  def apply[E <: Empty](errorType: E, label: String)(using errorShow: EmptyErrorShow[E]): EmptyError[E] =
    StandardError(errorType, label, (), (), (), errorShow.show(errorType, label, (), ()), NoError)
end EmptyError
