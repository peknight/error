package com.peknight.error

import com.peknight.error.labelled.LabelledValueError

package object std:
  type EmptyError[T <: Empty] = LabelledValueError[T, Unit]
  type UndefinedError = LabelledValueError[Undefined, Unit]
end std
