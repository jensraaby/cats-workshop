package cats.workshop

import cats.workshop.Maybe.{Just, NotThere}

/**
  * This is a bit like Scala's Option, but has different names and not all of the methods you normally have
  *
  * @tparam A
  */
sealed trait Maybe[+A] {
  def get: A

  def fold[B](z: B)(f: A => B): B = this match {
    case Just(a) => f(a)
    case NotThere => z
  }
}

object Maybe {
  def apply[A](value: A): Maybe[A] = Just(value)
  def empty[A]: Maybe[A] = NotThere

  case class Just[A](get: A) extends Maybe[A]

  case object NotThere extends Maybe[Nothing] {
    override def get = throw new NoSuchElementException("NotThere.get")
  }

}