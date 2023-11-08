package com.peknight.error.spire.math.interval

import com.peknight.error.std.{Empty, EmptyPlatform}

trait BoundEmpty extends Empty
object BoundEmpty extends BoundEmpty with EmptyPlatform

