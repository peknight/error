package com.peknight.error.traverse

import cats.Show
import cats.syntax.show.*
import com.peknight.error.ErrorType

trait Traverse extends ErrorType
case object Traverse extends Traverse:
  given [Actual]: TraverseErrorShow[Actual] with
    def show(errorType: Traverse, label: String, actual: Actual, expect: Unit): String =
      s"traverse $label failed"
  end given
end Traverse
