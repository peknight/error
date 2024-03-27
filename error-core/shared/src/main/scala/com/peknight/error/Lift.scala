package com.peknight.error

trait Lift[+E] extends Error:
  def error: E
end Lift
