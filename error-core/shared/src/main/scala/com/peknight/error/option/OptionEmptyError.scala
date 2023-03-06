package com.peknight.error.option

import com.peknight.error.EmptyError

trait OptionEmptyError extends EmptyError

object OptionEmptyError:
  def apply(label0: String): OptionEmptyError = new OptionEmptyError:
    def label: String = label0

  def unapply(error: OptionEmptyError): Some[String] = Some(error.label)

end OptionEmptyError


