package com.peknight.error.parse.cats

import cats.parse.Parser
import com.peknight.error.parse.ParseError

object CatsParseError:
  def apply(label: String, actual: Parser.Error, message: String): CatsParseError =
    ParseError(CatsParse, label, actual, message)

  def apply(label: String, actual: Parser.Error)(using CatsParseErrorShow): CatsParseError =
    ParseError(CatsParse, label, actual)
end CatsParseError
