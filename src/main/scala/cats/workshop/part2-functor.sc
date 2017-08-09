import cats.Functor
import cats.instances.list._
import cats.workshop.Maybe
import cats.workshop.Maybe.{Just, NotThere}


/**
  * The Functor is a bread and butter type class in Cats
  *
  * It's basically the interface provided by 'map' in most Scala collections
  */
val listFunctor = Functor[List]

val myList = List(1,2,3,4)
listFunctor.map(myList)(_ + 1)

// we get some enriched syntax on Lists with the syntax import
import cats.syntax.functor._

// What do these all do?
myList.tupleLeft("hello")
myList.imap(_ %2 == 0)(bool => if (bool) 1 else 0)
myList.fproduct(someInt => s"hello + $someInt")


// Have a go at writing a Functor instance for Maybe

implicit def maybeFunctor: Functor[Maybe] = new Functor[Maybe] {
  override def map[A, B](fa: Maybe[A])(f: (A) => B): Maybe[B] =
    fa.fold(Maybe.empty[B])(a => Just(f(a)))
}

Functor[Maybe].map(Just("thing"))(_.reverse)
Functor[Maybe].map[String, String](NotThere)(_.reverse)


// Functors can be combined / composed together:
val composedFunctor = Functor[List] compose Functor[Maybe]

// if we have a nested structure:
val listOfMaybe = List(Just(1), Just(4), NotThere)

// ... we can use map to work on the innermost type:
composedFunctor.map(listOfMaybe)(_ + 1)