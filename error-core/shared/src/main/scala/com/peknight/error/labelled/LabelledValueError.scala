package com.peknight.error.labelled

import com.peknight.error.Error.{EmptyError, StandardError}
import com.peknight.error.{ErrorShow, ErrorType}
import com.peknight.generic.tuple.{LabelledTuple, LabelledValue}
import com.peknight.generic.tuple.syntax.tuple

object LabelledValueError:
  def apply[T <: ErrorType, A](errorType: T, labelledValue: LabelledValue[A])
                              (show: (T, LabelledTuple[A *: EmptyTuple]) => String): LabelledValueError[T, A] =
    StandardError(errorType, labelledValue.tuple, EmptyError, ErrorShow(show))
  end apply