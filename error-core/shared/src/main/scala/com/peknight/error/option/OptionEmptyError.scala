package com.peknight.error.option

import com.peknight.error.std.EmptyError

object OptionEmptyError:
  def apply[Ext](label: String, ext: Ext, message: String): OptionEmptyErrorT[Ext] =
    EmptyError(OptionEmpty, label, ext, message)

  def apply(label: String, message: String): OptionEmptyError =
    EmptyError(OptionEmpty, label, message)

  def apply(label: String)(using OptionEmptyErrorShow): OptionEmptyError =
    EmptyError(OptionEmpty, label)
end OptionEmptyError


