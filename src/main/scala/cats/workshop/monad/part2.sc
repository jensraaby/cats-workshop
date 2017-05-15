import cats.{Applicative, Monad}
import cats.workshop.Maybe
import cats.workshop.Maybe.{Just, NotThere}
import cats.workshop.instances.maybe._

// If we have an applicative functor instance for Maybe, we can use it to bootstrap a monad instance

// have a go at implementing flatMap
implicit def maybeMonad(implicit app: Applicative[Maybe]) = new Monad[Maybe] {
  override def pure[A](x: A): Maybe[A] = app.pure(x)

  override def flatMap[A, B](fa: Maybe[A])(f: (A) => Maybe[B]): Maybe[B] = ???

  // ignore tailRecM for now
  override def tailRecM[A, B](a: A)(f: (A) => Maybe[Either[A, B]]): Maybe[B] = ???
}


// Now we can use our monad instance! Given a Maybe and a function that returns a Maybe, we can flatMap
Monad[Maybe].flatMap(Maybe(5))(halveIfEven)
Monad[Maybe].flatMap(Maybe(4))(halveIfEven)


def halveIfEven(a: Int): Maybe[Int] = if (a %2 == 0) Just(a/2) else NotThere
