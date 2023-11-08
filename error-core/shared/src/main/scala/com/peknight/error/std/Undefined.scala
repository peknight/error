package com.peknight.error.std

import com.peknight.error.Error

trait Undefined extends Error
object Undefined extends Undefined with UndefinedPlatform
