import cats.Id
import cats.data.{Kleisli, Reader}
import cats.workshop.Maybe

/*


  See if you can work out what a reader is.

 */


val readerStringInt: Reader[String, Int] = Reader(str => str.length)

// you pass the input when you want to run your computation:
readerStringInt.run("some string")


// Q: What is this useful for?








// Here is a contrived example of using a Reader:
class MyConfig

class MyDependency(myConfig: MyConfig)
class MyOtherDependency(myConfig: MyConfig)

class ThingWithDependencies(myThing: MyDependency, myOtherThing: MyOtherDependency)

val buildMyThing = Reader[MyConfig, MyDependency](config => new MyDependency(config))
val buildMyOtherThing = Reader[MyConfig, MyOtherDependency](config => new MyOtherDependency(config))

// Reader instances have a run method:
buildMyThing.run(new MyConfig)

// the real use is when you compose them:
val monad: Reader[MyConfig, ThingWithDependencies] = for {
  myThing <- buildMyThing
  myOtherThing <- buildMyOtherThing
} yield new ThingWithDependencies(myThing, myOtherThing)

monad.run(new MyConfig)


// Aside: Really a Reader is an alias of Kleisli[F,A, B]
val kleisliMaybe: Kleisli[Maybe, String, String] = Kleisli((a: String) => Maybe(a))

kleisliMaybe.run("some string")

// Kleisli is the pattern of function that has the shape A=>F[B] (e.g. something you pass to flatMap).

