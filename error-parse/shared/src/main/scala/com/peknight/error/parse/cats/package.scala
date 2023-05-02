package com.peknight.error.parse

import _root_.cats.parse.Parser

package object cats:
  type CatsParseError = ParseError[CatsParseFailed, Parser.Error]
  type CatsParseErrorShow = ParseErrorShow[CatsParseFailed, Parser.Error]
end cats
