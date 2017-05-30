import cats.workshop.Maybe
import cats.workshop.Maybe.Just

/*

We've seen the tie-fighter |@|, but why do we use it?

*/

// Here's a toy version of the Applicative typeclass:

trait Applicative[F[_]] {
  // pure lifts anything into this functor
  def pure[A](a: A): F[A]

  // "apply" applies a function inside the functor to a value inside the functor
  def ap[A, B](fa: F[A])(ff: F[A => B]): F[B]

  // same map as normal, but implemented using ap and pure
  def map[A, B](fa: F[A])(f: A => B): F[B] = ap(fa)(pure(f))

  // have a go at implementing map2 and map3 using 'ap'
  def map2[A,B,Z](fa: F[A], fb: F[B])(f: (A,B) => Z): F[Z] = ???
  def map3[A,B,C,Z](fa: F[A], fb: F[B], fc: F[C])(f: (A,B,C) => Z): F[Z] = ???
}


object MaybeApplicative extends Applicative[Maybe] {
  override def pure[A](a: A): Maybe[A] = ???

  override def ap[A, B](fa: Maybe[A])(ff: Maybe[(A) => B]): Maybe[B] = ???

}

// Now we can use it:
MaybeApplicative.map2(Just(2), Just(4))(_ + _)