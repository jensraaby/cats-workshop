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

  Apply adds the 'ap' function, which is like 'map' but takes a function wrapped inside the functor
 */

val stringLength = (s: String) => s.length

optionApply.ap(Some(stringLength))(Some("hello"))





/*
  Why do we need 'ap'?

  Imagine we have 2 values wrapped in an effect F (e.g. Option), and want to apply a function to them.

 */
val maybeInt = Some(5)
val maybeString = Some("string")

val combiningFunction = (a: Int, b: String) => a + b.length

// we cannot simply combine maybeInt and maybeString with map:
val partiallyApplied = maybeInt.map(int => (string: String) => combiningFunction(int, string))

// But now we have a function wrapped in an Option! We need to be able to apply it to maybeString.
// we can do this with the Apply instance for Option:

Apply[Option].ap(partiallyApplied)(Some("string"))






/*
    Have a go at implementing Apply for Maybe.

    (Hint: one of the functions can use the other)
 */

implicit object MaybeApply extends Apply[Maybe] {
  override def ap[A, B](ff: Maybe[(A) => B])(fa: Maybe[A]): Maybe[B] = ???

  override def map[A, B](fa: Maybe[A])(f: (A) => B): Maybe[B] = ???
}


// Try out the ap function:
Apply[Maybe].ap(Just(stringLength))(Just("a string"))
Apply[Maybe].ap(Just(stringLength))(NotThere)



/*

Once we have 'ap' and 'map', we get several functions for free.

Specifically, functions with higher arity such as:
 * ap2, ap3,.... ap22
 * map2, map3, ... map22
 * tuple2, tuple3 ... tuple22

*/
val combineStringLengths = (s1: String, s2: String) => s1.length + s2.length
optionApply.ap2(Some(combineStringLengths))(Some("one"), Some("three"))
optionApply.map2(Some("a"), Some("b"))(combineStringLengths)
optionApply.tuple2(Some(2), Some("and"))


// With these, we can combine any number of effectful values and apply them to a given function
val username = Some("user")
val password = Some("password")
val hostname = Some("www.secretlogin.com")

// the world's worst login method, which doesn't know about Option
def login(user: String, password: String, host: String): Boolean = (user, password, host) match {
  case ("admin", "password", _) => true
  case ("demo", "demo", _) => true
  case _ => false
}


// now we can combine all our options and pass them through to the login function
Apply[Option].map3(username, password, hostname)(login)

