import cats.Applicative
import cats.workshop.Maybe
import cats.workshop.Maybe.{Just, NotThere}
import cats.instances.list._
import cats.instances.option._

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



