import cats.{Cartesian, Functor}
import cats.workshop.Maybe
import cats.workshop.Maybe.{Just, NotThere}
import cats.workshop.instances._

/*

Cartesian is a way of 'zipping' values in a Context

 -> have a look at the typeclass cats.Cartesian

 */


/*
 Implement Cartesian for Maybe

  Note that the types in the arguments are independent but must have the same Context
*/
implicit object MaybeCartesian extends Cartesian[Maybe] {
  override def product[A, B](fa: Maybe[A], fb: Maybe[B]): Maybe[(A, B)] =
    (fa, fb) match {
      case (Just(a), Just(b)) => Just((a,b))
      case _ => NotThere
    }
}


for {
  x <- Option(1)
  y <- Option(x+2)
  z <- Option(3)
} yield y + z

Option(1)
  .flatMap(x => Option(x+2)
    .map(y =>
      Option(3).map(_ + y)
))
// Try it out with some different values:
MaybeCartesian.product(Just(2), Just("a string"))
MaybeCartesian.product(NotThere, Just(4))

import cats.instances.list._
Cartesian[List].product(List(1,2,3), List(4,5,6))

// This is just like Semigroups, but for Contexts rather than values

/*

Cartesian has a companion object with some higher arity helper functions
 */

implicit object MaybeFunctor extends Functor[Maybe] {
  override def map[A, B](fa: Maybe[A])(f: (A) => B): Maybe[B] =
    fa.fold(Maybe.empty[B])(a => Just(f(a)))
}


Cartesian.map2(Maybe(1), Maybe(2))(_ + _)
//MaybeFunctor.map(MaybeCartesian.product(Maybe(1), Maybe(2))) { case (a,b) => a+b }

//Cartesian.map3(Maybe(2), Maybe("string"), Maybe(true)) { case (int, string, bool) => s"$int $string $bool" }


Cartesian.tuple2(Maybe("string"), Maybe(true))

Cartesian.tuple3(Maybe(2), Maybe("string"), Maybe(true))


val value: Maybe[(String, Boolean)] = Cartesian.tuple2(Maybe("string"), Maybe(true))
//value map { case (a,b) => s"$a$b"}

/*
we can use these methods directly, or use the Cartesian builder syntax

Uncomment the following, and work out which import we need
 */
import cats.syntax.cartesian._

val builder = Maybe(2) |@| Maybe("string")

//|@| Maybe(false)
//builder.map { case (int, string, bool) => s"$int $string $bool" }

