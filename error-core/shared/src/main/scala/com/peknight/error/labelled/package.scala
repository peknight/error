package com.peknight.error

import com.peknight.error.Error.StandardError
import com.peknight.generic.tuple.LabelledTuple

package object labelled:
  type LabelledValueError[T <: ErrorType, A] = StandardError[T, LabelledTuple[A *: EmptyTuple]]
end labelled
