package com.peknight.error.traverse

import com.peknight.error.Error
import com.peknight.error.Error.StandardError

object TraverseError:
  extension (error: Error)
    def toTraverseError[Actual](label: String, actual: Actual, message: String): TraverseError[Actual] =
      error.to(Traverse, label, actual, (), message)

    def toTraverseError[Actual](label: String, actual: Actual)(using errorShow: TraverseErrorShow[Actual])
    : TraverseError[Actual] =
      error.to(Traverse, label, actual, (), errorShow.show(Traverse, label, actual, ()))
  end extension
end TraverseError
