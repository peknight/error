package com.peknight.error.spire.math

import com.peknight.error.std.{EmptyError, EmptyErrorT}

object IntervalEmptyError:
  def apply[Ext](label: String, ext: Ext, message: String): IntervalEmptyErrorT[Ext] =
    EmptyError(IntervalEmpty, label, ext, message)

  def apply(label: String, message: String): IntervalEmptyError =
    EmptyError(IntervalEmpty, label, message)

  def apply(label: String)(using IntervalEmptyErrorShow): IntervalEmptyError =
    EmptyError(IntervalEmpty, label)
end IntervalEmptyError
