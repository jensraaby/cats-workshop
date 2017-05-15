
/*

To motivate the examples in this workshop we'll use our own Option type called Maybe

 */

import cats.workshop.Maybe
import cats.workshop.Maybe._

val maybeInt: Maybe[Int] = Just(123)
val notThereInt: Maybe[Int] = NotThere

// at the moment, we just have a fold method on Maybe:
maybeInt.fold(0)(_ * 2)


/*
 Semigroups

  A semigroup is a set (e.g. all integers), and a way of combining any 2 of that set.

*/

import cats.Semigroup

// here's the easiest semigroup of them all - Integers!
import cats.instances.int._
Semigroup[Int].combine(2, 3)

// have a go at making a String Semigroup:
implicit val stringSemigroup: Semigroup[String] = ???

//Semigroup[String].combine("hello", "world")


/*

Now let's have a go at implementing Semigroup on Maybe.
  Maybe[A] is the set containing Just[A] and NotThere

Maybe has a type parameter A, so we need to be able to combine values of type A in order to
combine 2 Just[A] values in to one Just[A] value.


Hint: think about all the ways you could combine 2 Maybes
*/

implicit def maybeSemigroup[A](implicit semigroupA: Semigroup[A]): Semigroup[Maybe[A]] = ???

// Uncomment to try it out:
//Semigroup[Maybe[String]].combine(Just("hello"), Just("world"))
//Semigroup[Maybe[Int]].combine(Just(2), NotThere)


/*

Monoids

  A monoid is like a Semigroup, but has an "empty" value.
  This means you can use your monoid

  For example, the Semigroup for Integer addition would have n empty value of 0.
  The Semigroup for Integer multiplication would have a zero of 1.

 */

import cats.Monoid

implicit val integerMultiplication: Monoid[Int] = new Monoid[Int] {
  override def empty: Int = 1

  override def combine(x: Int, y: Int): Int = x * y
}

// Monoids have a combineAll method that will work for any TraversableOnce[A]
Monoid[Int].combineAll(List(1))
Monoid[Int].combineAll(List(1,2,3))

// we can use our monoid on the fold method:
// note this is manually wired in, using the implicit val explicitly
List(1,1,2,3,5,8,13,21).fold(integerMultiplication.empty)(integerMultiplication.combine)


// have a go at implementing a Monoid for Maybe
implicit def maybeMonoid[A](implicit aMonoid: Monoid[A]): Monoid[Maybe[A]] = ???

// again we can use it with fold:
List(Just(1), Just(2), NotThere).fold(maybeMonoid[Int].empty)(maybeMonoid[Int].combine)
