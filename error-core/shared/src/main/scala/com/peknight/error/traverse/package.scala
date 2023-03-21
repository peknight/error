package com.peknight.error

import com.peknight.error.Error.StandardError
import com.peknight.error.ErrorShow

package object traverse:
  type TraverseError[Actual] = StandardError[Traverse, Actual, Unit, EmptyTuple]
  type TraverseErrorShow[Actual] = ErrorShow[Traverse, Actual, Unit]
end traverse
