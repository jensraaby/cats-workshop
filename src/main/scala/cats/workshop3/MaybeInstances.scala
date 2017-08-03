package cats.workshop3

import cats.{Applicative, Apply, Functor}
import cats.workshop.Maybe
import cats.workshop.Maybe.{Just, NotThere}

object MaybeInstances {
  implicit val maybeFunctor: Functor[Maybe] = new Functor[Maybe] {
    override def map[A, B](fa: Maybe[A])(f: (A) => B): Maybe[B] = fa match {
      case Just(a)  => Just(f(a))
      case NotThere => NotThere
    }
  }

  implicit val maybeApply: Apply[Maybe] = new Apply[Maybe] {
    override def ap[A, B](ff: Maybe[(A) => B])(fa: Maybe[A]): Maybe[B] = ???

    override def map[A, B](fa: Maybe[A])(f: (A) => B): Maybe[B] = ???
  }

  implicit val maybeApplicative: Applicative[Maybe] = new Applicative[Maybe] {
    override def pure[A](x: A): Maybe[A] = ???

    override def ap[A, B](ff: Maybe[(A) => B])(fa: Maybe[A]): Maybe[B] = ???
  }
}
