package com.peknight.error.spire.math.interval

import com.peknight.error.EmptyError

trait BoundEmptyError extends EmptyError

object BoundEmptyError:
  def apply(label0: String): BoundEmptyError = new BoundEmptyError:
    def label: String = label0

  def unapply(error: BoundEmptyError): Some[String] = Some(error.label)

end BoundEmptyError
