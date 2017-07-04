import cats.workshop.Maybe
import cats.workshop.Maybe.Just

import cats.Traverse

val listOfMaybe: List[Maybe[String]] = List(Just("there"), Just("that"), Just("looks"), Just("better"))

// implement sequence, and try it out with the above list
def sequence(listOfMaybeString: List[Maybe[String]]): Maybe[List[String]] = ???


// now try using the sequence method (which comes from the cats Traverse syntax) on the list of Maybe
import cats.syntax.traverse._
