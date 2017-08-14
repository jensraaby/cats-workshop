import cats.data.{EitherT, OptionT}
import cats.instances.all._
import cats.syntax.either._
import cats.syntax.flatMap._
import cats.syntax.applicative._
import com.twitter.util.{Await, Future}
import io.catbird.util._

// here's a basic example of an annoying structure - a Future of Either
// The error type is String, and the success type is Int
val nestedResult = Future.value(1.asRight[String])
val badNestedResult = Future.value("error".asLeft[Int])

// How can we check if the two futures contain successes, and combine them if so?




// Introducing the EitherT monad transformer:
def combineResults(nestedResult1: Future[Either[String, Int]], nestedResult2: Future[Either[String, Int]]) =
  for {
    first  <- EitherT(nestedResult1)
    second <- EitherT(nestedResult2)
  } yield s"$first and $second"

Await.result(combineResults(nestedResult, badNestedResult).value)
Await.result(combineResults(nestedResult, nestedResult).value)


/*

 Backing out a bit, let's think about representing different error states

 */

// a few type aliases
type Error = String
type ErrorOr[A] = Either[Error, A]
type ErrorOptionOr[A] = OptionT[ErrorOr, A]

// Both ErrorOr and ErrorOptionOr are monads, so we have a pure method:
val x = 123.pure[ErrorOr]
val y = "456".pure[ErrorOptionOr]
val z = "789".pure[ErrorOptionOr]

// given they are monads, we can use flatMap!
x.flatMap(number => (number * 2).pure[ErrorOr])
y.flatMap(str => (str + " it worked").pure[ErrorOptionOr])

// Notice how we didn't have to unwrap any of the nested options?

// Let's add a Future to the stack!
type FutureEither[A] = EitherT[Future, Error, A]
type FutureEitherOption[A] = OptionT[FutureEither, A]
val arghhh = 1234.pure[FutureEitherOption]
Await.result(arghhh.value.value)

// So why all the transformers nested together?
// It lets us use nice for comprehension syntax!
val combined = for {
  a <- 10.pure[FutureEitherOption]
  b <- 20.pure[FutureEitherOption]
} yield a + b

// we actually do want the value eventually! We can unpack it using .value on each transformer
def unpack[A](futureEitherOption: FutureEitherOption[A]): Future[Either[Error, Option[A]]] =
  futureEitherOption.value.value

// now we can get at the final result
Await.result(unpack(combined))