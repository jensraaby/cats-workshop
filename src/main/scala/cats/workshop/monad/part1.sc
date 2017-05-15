
import cats.Monad
import cats.instances.list._
import cats.syntax.all._
import cats.workshop.Maybe
import cats.workshop.Maybe.{Just, NotThere}

// this is the function known as bind in Haskell. It is defined for all monads:
List(1, 2) >>= (int => List(int, int*2))

// Bind ('>>=') is basically flatMap:
List(1, 2).flatMap(int => List(int, int*2))


/*

  Monads extend applicative functors with a flatten operator

*/

Monad[List].flatten(List(List(1), List(2), List(3,4)))

// flatten has the type:
// def flatten(ffa: F[F[A]]): F[A]

// try to implement flatten for Maybe:

def flatten[A](ffa: Maybe[Maybe[A]]): Maybe[A] = ???

flatten(Maybe(Maybe(1)))

