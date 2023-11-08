package com.peknight.error.option

import com.peknight.error.std.{Empty, EmptyPlatform}

trait OptionEmpty extends Empty
object OptionEmpty extends OptionEmpty with EmptyPlatform


