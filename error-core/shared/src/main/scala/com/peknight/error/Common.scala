package com.peknight.error

trait Common[+E, +T] extends Lift[E] with Value[Option[T]]
