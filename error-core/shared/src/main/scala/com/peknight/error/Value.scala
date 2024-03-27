package com.peknight.error

trait Value[+A] extends Error:
  def value: A
end Value
