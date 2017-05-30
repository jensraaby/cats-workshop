import cats.Applicative
import cats.workshop.Maybe
import cats.workshop.Maybe.{Just, NotThere}
import cats.instances.list._
import cats.instances.option._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

/*
   Applicative is an extension of Apply, but it adds the "pure" function.

 */

implicit object MaybeApplicative extends Applicative[Maybe] {
  // Pure puts a single value into the context of your Functor
  override def pure[A](x: A): Maybe[A] = ???

  // as before, ap takes a function wrapped into your Functor and a value which is also wrapped into your Functor.
  // it applies to the function to the value.
  override def ap[A, B](ff: Maybe[(A) => B])(fa: Maybe[A]): Maybe[B] = ???

  //  Note that 'map' is defined in terms of 'pure' and 'ap':
  //
  // override def map[A, B](fa: F[A])(f: A => B): F[B] =
  //  ap(pure(f))(fa)
}


val stringLength = (s: String) => s.length


// Try out your implementation:
//val appStringLength = Applicative[Maybe].pure(stringLength)
//Applicative[Maybe].pure(12345)
//Applicative[Maybe].ap(appStringLength)(Just("this is a string!"))
//Applicative[Maybe].ap(appStringLength)(Maybe.empty[String])
//Applicative[Maybe].ap(Maybe.empty[String => Int])(Just("this is a string!"))



/*
 You can compose applicative functors, just like normal functors
  */

val listAndOption = Applicative[List] compose Applicative[Option]
listAndOption.pure(1)

listAndOption.ap(listAndOption.pure(stringLength))(List(Some("string"), Some("other string")))


/*

  Applicatives define a product function, which is similar to the Cartesian builder for creating a tupled value

*/


Applicative[Option].product(Some(2), Some("thing"))


/*

  One of the most useful methods that come with Applicatives is 'sequence':


 */

import cats.instances.try_._
import cats.instances.future._
Applicative[Option].sequence(List(Option(2), Option(3)))

// This is particularly useful for error handling
Applicative[Try].sequence(List(Success(2), Failure(new RuntimeException("it went wrong"))))
Applicative[Try].sequence(List(Try(2), Try(4)))

// Try changing the above list of Try values to Success values and see what happens

// this is something we use quite a lot with futures
val sequencedFutures = Applicative[Future].sequence(List(Future(1), Future(2)))
Await.result(sequencedFutures, Duration("2s"))

