
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
// Note we have 'summoned' the Semigroup for int using Semigroup.apply[Int].

// have a go at making a String Semigroup:
implicit val stringSemigroup: Semigroup[String] = new Semigroup[String] {
  override def combine(x: String, y: String): String = x + y
}

Semigroup[String].combine("hello", "world")

/*

  NB. The above syntax for creating an instance will work with Scala 2.11.x -
  In 2.12 we can use a more concise syntax for type-classes that have only 1 abstract method:

  implicit val stringSemigroup: Semigroup[String] = (x: String, y: String) => x + y

*/



/*

Now let's have a go at implementing Semigroup on Maybe.
  Maybe[A] is the set containing Just[A] and NotThere

Maybe has a type parameter A, so we need to be able to combine values of type A in order to
combine 2 Just[A] values in to one Just[A] value.


Hint: think about all the ways you could combine 2 Maybes
*/

implicit def maybeSemigroup[A](implicit semigroupA: Semigroup[A]): Semigroup[Maybe[A]] =
  (x: Maybe[A], y: Maybe[A]) => (x, y) match {
    case (Just(a1), Just(a2)) => Just(semigroupA.combine(a1, a2))
    case _ => NotThere
  }

// Let's try it out:
Semigroup[Maybe[String]].combine(Just("hello"), Just("world"))
Semigroup[Maybe[Int]].combine(Just(2), NotThere)


/*

Monoids

  A monoid is like a Semigroup, but has an "empty" value.
  This means you can use your Monoid for folding on a List of any size.

  For example, the Monoid for Integer addition would have n empty value of 0.
  The Monoid for Integer multiplication would have a zero of 1.

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
implicit def maybeMonoid[A](implicit aMonoid: Monoid[A]): Monoid[Maybe[A]] = new Monoid[Maybe[A]] {
  override def empty: Maybe[A] = NotThere

  override def combine(x: Maybe[A], y: Maybe[A]): Maybe[A] = (x, y) match {
    case (Just(a1), Just(a2)) => Just(aMonoid.combine(a1, a2))
    case (Just(a), NotThere) => Just(a)
    case (NotThere, Just(a)) => Just(a)
    case _ => NotThere
  }
}

// again we can use it with fold:
List(Just(1), Just(2), NotThere).fold(maybeMonoid[Int].empty)(maybeMonoid[Int].combine)

// Import the semigroup syntax for the |+| operator
import cats.syntax.semigroup._


// Let's ensure the laws on Monoids hold for our Maybe type:
// we'll use some Maube[Int] values:
val (x, y, z) = (Maybe(1), Maybe(2), Maybe(3))

// 1. Associativity:
((x |+| y) |+| z) == (x |+| (y |+| z))

// 2. left identity:
(Monoid[Maybe[Int]].empty |+| x) == x

// 3. right identity
(x |+| Monoid[Maybe[Int]].empty) == x
