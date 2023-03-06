package com.peknight.error.spire.math

import com.peknight.error.EmptyError

trait IntervalEmptyError extends EmptyError

object IntervalEmptyError:
  def apply(label0: String): IntervalEmptyError = new IntervalEmptyError:
    def label: String = label0

  def unapply(error: IntervalEmptyError): Some[String] = Some(error.label)

end IntervalEmptyError
