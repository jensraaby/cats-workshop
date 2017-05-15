import cats.{Apply, Cartesian, Functor}
import cats.instances.option._
import cats.workshop.Maybe
import cats.workshop.Maybe.{Just, NotThere}

// We have already seen Functors, which have the map method defined

val optionFunctor = Functor[Option]
optionFunctor.map(Some("a value"))(_.length)




// Applicative functors also implement a Typeclass called 'Apply'
val optionApply = Apply[Option]


/*

  Apply adds the 'ap' function, which is like 'map' but takes a function wrapped inside the functor
 */

val stringLength = (s: String) => s.length

optionApply.ap(Some(stringLength))(Some("hello"))




/*
    Have a go at implementing Apply for Maybe.

    (Hint: one of the functions can use the other)
 */

implicit object MaybeApply extends Apply[Maybe] {
  override def ap[A, B](ff: Maybe[(A) => B])(fa: Maybe[A]): Maybe[B] = ???

  override def map[A, B](fa: Maybe[A])(f: (A) => B): Maybe[B] = ???
}

// Try out the ap function:
Apply[Maybe].ap(Just(stringLength))(Just("a string"))
Apply[Maybe].ap(Just(stringLength))(NotThere)




/*

Once we have 'ap' and 'map', we get several functions for free.

Specifically, functions with higher arity such as:
 * ap2, ap3,.... ap22
 * map2, map3, ... map22
 * tuple2, tuple3 ... tuple22

*/
val combineStringLengths = (s1: String, s2: String) => s1.length + s2.length
optionApply.ap2(Some(combineStringLengths))(Some("one"), Some("three"))
optionApply.map2(Some("a"), Some("b"))(combineStringLengths)
optionApply.tuple2(Some(2), Some("and"))


/*
  These functions are what enables the |@| syntax (with up to 22 items).

 To get the Macaulay Culkin operator, we need to import the Cartesian syntax.
*/
import cats.syntax.cartesian._

// An example with a list:
import cats.instances.list._
val scream = List(2,3) |@| List(4,5) |@| List("string", "two")

// The |@| function creates a CartesianBuilder
// Each time we call a CartesianBuilder with |@|, we get the next CartesianBuilder.
// Again, they increase in number as you add arguments.
// CartesianBuilder1, CartesianBuilder2, CartesianBuilder3.... CartesianBuilder22

// to combine the values and get them out of the cartesian builder, you can use tupled:
scream.tupled
// what does this give you?


// we could also map over the result with a function:
scream.map((num1, num2, string) => num1 + num2 + string.length)

// Try using |@| with Maybe:
val maybeScream = Maybe(2) |@| Maybe(4)
maybeScream.tupled

val anotherMaybeScream = Maybe.empty[String] |@| Maybe("string")
anotherMaybeScream.tupled
