package com.peknight.error.spire.math.interval

import com.peknight.error.Error

trait Unbound extends Error
object Unbound extends Unbound with UnboundPlatform
