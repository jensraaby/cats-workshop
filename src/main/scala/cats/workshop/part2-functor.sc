import cats.Functor
import cats.instances.list._
import cats.workshop.Maybe
import cats.workshop.Maybe.{Just, NotThere}

val listFunctor = Functor[List]

val myList = List(1,2,3,4)
listFunctor.map(myList)(_ + 1)

// we get some enriched syntax on Lists with the syntax import
import cats.syntax.functor._

myList.tupleLeft("hello")
myList.imap(_ %2 == 0)(bool => if (bool) 1 else 0)
myList.fproduct(someInt => s"hello + $someInt")


// have a go at writing a Functor instance for Maybe

implicit def maybeFunctor: Functor[Maybe] = ???

Functor[Maybe].map(Just("thing"))(_.reverse)
Functor[Maybe].map[String, String](NotThere)(_.reverse)


// now we can compose List and Maybe together

val composedFunctor = Functor[List] compose Functor[Maybe]

// make a nested structure:
val listOfMaybe = List(Just(1), Just(4), NotThere)

// and now we can map on the innermost type
composedFunctor.map(listOfMaybe)(_ + 1)