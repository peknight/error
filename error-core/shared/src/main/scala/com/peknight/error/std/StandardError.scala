package com.peknight.error.std

import com.peknight.error.Error

case class StandardError(override val errorType: String, override val message: String) extends Error
