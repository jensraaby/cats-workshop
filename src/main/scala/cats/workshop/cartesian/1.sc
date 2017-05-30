import cats.Cartesian
import cats.workshop.Maybe
import cats.workshop.Maybe.{Just, NotThere}


/*

Cartesian is a way of 'zipping' values in a Context

 -> have a look at the typeclass cats.Cartesian

 */


/*
 Implement Cartesian for Maybe

  Note that the types in the arguments are independent but must have the same Context
*/
implicit object MaybeCartesian extends Cartesian[Maybe] {
  override def product[A, B](fa: Maybe[A], fb: Maybe[B]): Maybe[(A, B)] = ???
}

// Try it out with some different values:
MaybeCartesian.product(Just(2), Just("a string"))
MaybeCartesian.product(NotThere, Just(4))

// This is just like Semigroups, but for Contexts rather than values

/*

Cartesian has a companion object with some higher arity helper functions
 */

Cartesian.map3(Maybe(2), Maybe("string"), Maybe(true)) { case (int, string, bool) => s"$int $string $bool" }
Cartesian.tuple2(Maybe("string"), Maybe(true))


/*
we can use these methods directly, or use the Cartesian builder syntax

Uncomment the following, and work out which import we need
 */


//val builder = Maybe(2) |@| Maybe("string") |@| Maybe(false)
//builder.map { case (int, string, bool) => s"$int $string $bool" }

