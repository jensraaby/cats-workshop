import cats.Foldable

/*

If you have a monoid, you can use cats' Foldable type class!
 */

import cats.instances.list._ // get the Foldable for List
import cats.instances.int._ // get the monoid for Int

/*
Foldable gives some very useful functions (more than I've shown here)
 */

Foldable[List].combineAll(List(1,2,3))
Foldable[List].foldMap(List(1,2,3))(_ * 2)
Foldable[List].exists(1.to(100, 2).toList)(someInt => someInt % 3 == 0)
