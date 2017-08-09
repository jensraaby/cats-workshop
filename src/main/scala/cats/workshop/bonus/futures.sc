import cats.Applicative
import io.catbird.util._
import cats.instances.list._
import cats.instances.option._
import com.twitter.util._

val futures = List(Future.value("hello"), Future.value("world"))
val futuresWithFailures = List(Future.value(1), Future.value(6), Future.exception(new RuntimeException("This is not good")))
val futuresWithOptions = (1 to 5 map (i => Future.value(Option(i)))).toList

// the standard Twitter Future library has the collect method:
val combinedSuccesses = Future.collect(futures)
val combinedMixture = Future.collect(futuresWithFailures)
val combinedOptions = Future.collect(futuresWithOptions)


// Now: let's get the Applicatives out:
private val app = Applicative[Future]

// Sequence is basically Future.collect:
val applicativeSequence = app.sequence(futures)
val applicativeSequenceWithFailures = app.sequence(futuresWithFailures)
val applicativeSequenceWithOptions = app.sequence(futuresWithOptions)

// You also get traverse, which is like doing a flatMap on all the Futures at once!
val simpleTraverse = app.traverse(futures)(_.transform {
  case Return(a) => Future(a)
  case Throw(_) => Future("this is a backup")
})
val failureTraverse = app.traverse(futuresWithFailures)(a => Future.value(a.toString))
Await.result(simpleTraverse)
Await.result(failureTraverse) // note, we have propagated the failure, but we still have the succeses


/* Magic happens when you compose */

val magic = Applicative[Future] compose Applicative[Option]
magic.sequence(futuresWithOptions)



// what about failures?
// Try to make a collectToTry implementation using the Future applicative
// hint: use the liftToTry method on the individual futures
app.traverse(futuresWithFailures)(_.liftToTry)

