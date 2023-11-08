package com.peknight.error.std

import com.peknight.error.Error

trait Empty extends Error
object Empty extends Empty with EmptyPlatform
