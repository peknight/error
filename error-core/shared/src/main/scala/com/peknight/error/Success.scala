package com.peknight.error

trait Success extends Error:
  override def success: Boolean = true
end Success
object Success extends Success
