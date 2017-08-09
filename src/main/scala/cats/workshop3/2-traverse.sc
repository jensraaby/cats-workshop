import cats.workshop.Maybe
import cats.workshop.Maybe.{Just, NotThere}
import cats.{Applicative, Traverse}

val listOfMaybe: List[Maybe[String]] = List(Just("there"), Just("that"), Just("looks"), Just("better"))

// implement sequence, and try it out with the above list
def sequence(listOfMaybeString: List[Maybe[String]]): Maybe[List[String]] =
  listOfMaybeString.foldLeft(Maybe(List.empty[String])) {
    case (Just(acc), Just(cur)) => Just(acc :+ cur)
    case _ => NotThere
  }

// try it out:
sequence(listOfMaybe)


// now try using the sequence method (which comes from the cats Traverse syntax) on the list of Maybe
import cats.syntax.traverse._

// we need an instance of Applicative for Maybe, and functor for List
import cats.instances.list._

implicit val maybeApplicative: Applicative[Maybe] = new Applicative[Maybe] {
  override def pure[A](a: A): Maybe[A] = Just(a)

  override def ap[A, B](ff: Maybe[(A) => B])(fa: Maybe[A]): Maybe[B] = (ff, fa) match {
    case (Just(f), Just(a)) => pure(f(a))
    case _ => NotThere
  }
}

// with those in scope, we can use sequence:
val seq = listOfMaybe.sequence

// we can also use traverse:
List(1,4,2).traverse(getName)

// this is a slightly contrived function that returns a Maybe
def getName(key: Int): Maybe[String] = {
  val names = Map(
    1 -> "one",
    2 -> "two",
    3 -> "three",
    4 -> "four"
  )
  // this is a bit hacky because we're not using real Options!
  names.get(key) match {
    case Some(name) => Just(name)
    case None => NotThere
  }
}

