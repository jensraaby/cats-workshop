package cats.workshop.bonus

import cats.Applicative
import com.twitter.util.{Await, Future}
import io.catbird.util._
import cats.instances.list._

object ApplicativesForErrorHandling extends App {

  private val futures = List(Future.value("hello"), Future.value("world"))
  private val futuresWithFailures = List(Future.value(1), Future.value(6), Future.exception(new RuntimeException("This is not good")))
  private val futuresWithOptions = 1 to 5 map (i => Future.value(Option(i)))

  // the standard Twitter Future library has the collect method:
  private val combinedSuccesses = Future.collect(futures)
  private val combinedMixture = Future.collect(futuresWithFailures)
  private val combinedOptions = Future.collect(futuresWithOptions)


  // Now: using Applicatives:
  private val futureApplicative = Applicative[Future]

  val applicativeSequence = futureApplicative.sequence(futures)
  val applicativeSequenceWithFailures = futureApplicative.sequence(futuresWithFailures)

  println(applicativeSequence)
  println(applicativeSequenceWithFailures)


  val applicativeTraverse = futureApplicative.traverse(futures)(a => a.map(_.toUpperCase + " !!! "))
  println(Await.result(applicativeTraverse))

}
