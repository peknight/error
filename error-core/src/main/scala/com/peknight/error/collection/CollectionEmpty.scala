package com.peknight.error.collection

import com.peknight.error.std.Empty

trait CollectionEmpty extends Empty with CollectionError
object CollectionEmpty extends CollectionEmpty
