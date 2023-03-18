package com.peknight.error.syntax

import cats.Show
import com.peknight.error.Error.StandardError
import com.peknight.error.{ErrorShow, ErrorType}
import com.peknight.generic.tuple.{LabelledTuple, LabelledValue}

package object labelled:
  extension [T <: ErrorType, Repr <: Tuple] (error: StandardError[T, LabelledTuple[Repr]])
    def *:[A](labelledValue: LabelledValue[A])(using Show[A]): StandardError[T, LabelledTuple[A *: Repr]] =
      StandardError(error.errorType, labelledValue *: error.value, error.cause, ErrorShow { (_, _) =>
        s"${error.show.show(error.errorType, error.value)} << ${labelledValue._1}(${Show[A].show(labelledValue._2)})"
      })
  end extension
end labelled
