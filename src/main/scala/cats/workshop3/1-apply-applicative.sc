import cats.workshop.Maybe


// Implement Apply and Applicative in the MaybeInstances object
// hint: open MaybeInstances.scala
import cats.workshop3.MaybeInstances._



// try out some of the syntax here:
import cats.syntax.applicative._

val maybeDoubleIt = ((number: Int) => number * 2).pure[Maybe]
val maybeInt = 123.pure[Maybe]

maybeApplicative.ap(maybeDoubleIt)(maybeInt)

// the Cartesian builder syntax is enabled when you have an Apply or Applicative instance in scope
import cats.syntax.cartesian._

// note this is deprecated in Cats v1.0:
(Maybe("2") |@| Maybe.empty[String]).map { (s1, s2 ) =>
  s"$s1 and $s2"
}

// The new mapN syntax is from Apply:
import cats.syntax.apply._

("2".pure[Maybe], "hello".pure[Maybe]).mapN { (s1, s2) =>
  s"$s1 and $s2"
}


// there is also tuple syntax:
("2".pure[Maybe], "hello".pure[Maybe]).tupled

// Note that this new syntax relied on a compiler fix - see notes on SI-2712 here:
// https://github.com/typelevel/cats/releases/tag/v1.0.0-MF