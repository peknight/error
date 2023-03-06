package com.peknight.error

trait UndefinedError extends LabelledError:
  def message: String = s"$label is undefined"
end UndefinedError

object UndefinedError:
  def apply(label0: String): UndefinedError = new UndefinedError:
    def label: String = label0

  def unapply(error: UndefinedError): Some[String] = Some(error.label)

end UndefinedError
