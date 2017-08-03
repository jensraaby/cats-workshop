import cats.data.{Kleisli, Reader}
import cats.workshop.Maybe

// What is a Reader?

val readerStringInt: Reader[String, Int] = Reader(str => str.length)

// you pass the input when you want to run your computation:
readerStringInt.run("some string")


// Q: What is this useful for?



class MyConfig

class MyThing(myConfig: MyConfig)
class MyOtherThing(myConfig: MyConfig, myThing: MyThing)

val buildMyThing = Reader[MyConfig, MyThing](config => new MyThing(config))


val myOtherThing = for {
  b <- buildMyThing
} yield Reader((config: MyConfig) => new MyOtherThing(config, b))

// oops
myOtherThing.run(new MyConfig).run(new MyConfig)
















// Aside: Really a Reader is an alias of Kleisli[F,A, B]
val kleisliMaybe: Kleisli[Maybe, String, String] = Kleisli((a: String) => Maybe(a))

kleisliMaybe.run("some string")

// Kleisli is the pattern of function that has the shape A=>F[B] (e.g. something you pass to flatMap).

