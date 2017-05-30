/*
  The functions in Apply are what enables the |@| syntax (with up to 22 items).

 To get the Macaulay Culkin operator on an arbitrary Functor, we need to import the Cartesian syntax.
*/
import cats.syntax.cartesian._
import cats.instances.option._
import cats.instances.list._

// Here's an example with lists:
val scream = List(2,3) |@| List(4,5) |@| List("string", "two")


// The |@| function creates a CartesianBuilder
// Each time we call a CartesianBuilder with |@|, we get the next CartesianBuilder.
// They increment in number as you add arguments.
// CartesianBuilder1, CartesianBuilder2, CartesianBuilder3.... CartesianBuilder22

// To combine the values and get them out of the cartesian builder, you can use tupled:
scream.tupled
// what does this give you?






// we could also map over the result with a function:
scream.map((num1, num2, string) => num1 + num2 + string.length)

// Try using |@| with Option:
val maybeScream = Option(2) |@| Option(4)
maybeScream.tupled

val anotherMaybeScream = Option.empty[String] |@| Option("string")
anotherMaybeScream.tupled




// With our login example, we can use the |@| operator instead of Apply[Option].map3

val username: Option[String] = Some("user") // we need to force the type to be Option[String] rather than Some[String]
val password = Some("password")
val hostname = Option("www.secretlogin.com") // this is the other way to get the type to be Option[String]

// the world's worst login method, which doesn't know about Option
def login(user: String, password: String): Boolean = (user, password) match {
  case ("admin", "password") => true
  case ("demo", "demo") => true
  case _ => false
}

// This looks nicer than the map3 syntax (and it scales nicely as you add arguments to the login method)
(username |@| password) map login
(Option("admin") |@| Option("password")) map login
