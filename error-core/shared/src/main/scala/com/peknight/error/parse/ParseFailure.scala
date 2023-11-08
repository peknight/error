package com.peknight.error.parse

import com.peknight.error.Error

trait ParseFailure extends Error
object ParseFailure extends ParseFailure with ParseFailurePlatform
