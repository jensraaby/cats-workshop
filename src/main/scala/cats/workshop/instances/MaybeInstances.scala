package cats.workshop.instances

import cats.Applicative
import cats.workshop.Maybe
import cats.workshop.Maybe.{Just, NotThere}

trait MaybeInstances {

  implicit object ApplicativeMaybe extends Applicative[Maybe] {
    override def pure[A](x: A): Maybe[A] = Maybe(x)

    override def ap[A, B](ff: Maybe[(A) => B])(fa: Maybe[A]): Maybe[B] = (ff, fa) match {
      case (Just(f), Just(a)) => pure(f(a))
      case _ => NotThere
    }
  }
}
