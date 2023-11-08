package com.peknight.error.collection

import com.peknight.error.std.{Empty, EmptyPlatform}

trait CollectionEmpty extends Empty
object CollectionEmpty extends CollectionEmpty with EmptyPlatform
