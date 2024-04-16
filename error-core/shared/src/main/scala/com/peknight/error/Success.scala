package com.peknight.error

trait Success extends Error:
  override def isSuccess: Boolean = true
end Success
object Success extends Success
