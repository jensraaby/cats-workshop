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
    override def ap[A, B](ff: Maybe[(A) => B])(fa: Maybe[A]): Maybe[B] = (ff, fa) match {
      case (Just(f), Just(a)) => Just(f(a))
      case _                  => NotThere
    }

    override def map[A, B](fa: Maybe[A])(f: (A) => B): Maybe[B] =
      fa.fold(Maybe.empty[B])(a => Just(f(a)))
  }

  implicit val maybeApplicative: Applicative[Maybe] = new Applicative[Maybe] {
    override def pure[A](a: A): Maybe[A] = Just(a)

    override def ap[A, B](ff: Maybe[(A) => B])(fa: Maybe[A]): Maybe[B] = (ff, fa) match {
      case (Just(f), Just(a)) => pure(f(a))
      case _                  => NotThere
    }
  }
}
