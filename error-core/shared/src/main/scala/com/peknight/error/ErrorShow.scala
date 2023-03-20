package com.peknight.error

trait ErrorShow[-E <: ErrorType, -Actual, -Expect]:
  def show(errorType: E, label: String, actual: Actual, expect: Expect): String
end ErrorShow

object ErrorShow:
  def apply[E <: ErrorType, Actual, Expect](func: (E, String, Actual, Expect) => String)
  : ErrorShow[E, Actual, Expect] = func(_, _, _, _)
end ErrorShow