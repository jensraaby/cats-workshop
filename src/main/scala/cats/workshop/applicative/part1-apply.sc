import cats.{Apply, Functor}
import cats.instances.option._
import cats.workshop.Maybe
import cats.workshop.Maybe.{Just, NotThere}

// We have already seen Functors, which have the map method defined

val optionFunctor = Functor[Option]
optionFunctor.map(Some("a value"))(_.length)




// Applicative functors also implement a Typeclass called 'Apply'
val optionApply = Apply[Option]


/*

  Apply adds the 'ap' function, which is like map but takes a function inside the wrapper
 */

val stringLength = (s: String) => s.length

optionApply.ap(Some(stringLength))(Some("hello"))




/*
    Have a go at implementing Apply for Maybe.

    (Hint: one of the functions can use the other)
 */

implicit object MaybeApply extends Apply[Maybe] {
  override def ap[A, B](ff: Maybe[(A) => B])(fa: Maybe[A]): Maybe[B] = ???

  override def map[A, B](fa: Maybe[A])(f: (A) => B): Maybe[B] = ???
}

MaybeApply.ap(Just(stringLength))(Just("a string"))
MaybeApply.ap(Just(stringLength))(NotThere)