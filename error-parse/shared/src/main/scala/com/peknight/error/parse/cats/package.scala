package com.peknight.error.parse

import _root_.cats.parse.Parser

package object cats:
  type CatsParseError = ParseError[CatsParse, Parser.Error]
  type CatsParseErrorShow = ParseErrorShow[CatsParse, Parser.Error]
end cats
