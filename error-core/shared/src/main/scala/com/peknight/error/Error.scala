package com.peknight.error

import cats.Semigroup

trait Error:
  def message: String
  override def toString: String = s"${getClass.getName.split("\\$").head.split("\\.").last}($message)"
end Error

object Error:
  given Semigroup[Error] with
    def combine(x: Error, y: Error): Error = (x, y) match
      case (Errors(xes), Errors(yes)) => Errors(xes ++ yes.toList)
      case (Errors(xes), ye) => Errors(xes.head, xes.tail :+ ye)
      case (xe, Errors(yes)) => Errors(xe, yes.toList)
      case (xe, ye) => Errors.of(xe, ye)
  end given

end Error
