import cats.Apply
import cats.Applicative
import cats.workshop.Maybe


// Implement Apply and Applicative in the MaybeInstances object
import cats.workshop3.MaybeInstances._



// try out some of the syntax here:
import cats.syntax.applicative._

val maybeDoubleIt = ((number: Int) => number*2).pure[Maybe]
val maybeInt = 123.pure[Maybe]

maybeApplicative.ap(maybeDoubleIt)(maybeInt)