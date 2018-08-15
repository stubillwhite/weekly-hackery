package projecteuler

import scala.annotation.tailrec
import scala.collection.mutable


object Problem014 {

  implicit class TakeUntilStreamWrapper[T](list: Iterator[T]) {
    def takeUntil(predicate: T => Boolean): Iterator[T] = {
      list.span(!predicate(_)) match {
        case (head, tail) => (head.toList ++ tail.take(1).toList).toIterator
      }
    }
  }

  private val nextNumber = memoize {
    (n: Long) => {
      if (n % 2 == 0) n / 2
      else 3 * n + 1
    }
  }

  def collatz(n: Long): Iterator[Long] =
    Iterator.iterate(n)(nextNumber).takeUntil { _ == 1 }

  def collatzLength(n: Long): Long = {

    lazy val collatzLengthIter: (Long) => Long = memoize {
      (number: Long) => {
        if (number == 1) 1
        else 1 + collatzLengthIter(nextNumber(number))
      }
    }

    collatzLengthIter(n)
  }

  def memoize[A, B](f: (A) => B): A => B = {
    // TODO: Mutable; problem? Race conditions will be inefficient but should result in the right value, what about ConcurrentModificationException?
    // TODO: Syntactic sugar somehow, a bit gnarly to use.
    // TODO: How do you handle diffierent arities of f?
    val cache = mutable.HashMap[A, B]()

    val getOrCalculate = (a: A) => {
//      if (cache.contains(a)) println(s"Hit ${a}") else println(s"Miss ${a}")
      cache.getOrElseUpdate(a, f(a))
    }

    getOrCalculate
  }


  def main(args: Array[String]): Unit = {
    val startTime = System.currentTimeMillis()

    //    val result = (1L to 1000000L)
    //      .map(x => (x, collatz(x).length))
    //      .maxBy(_._2)

    val result = (1L to 1000000L)
      .map(x => (x, collatzLength(x)))
      .maxBy(_._2)

    //    println(collatzLength(13))

    println(result)

    val endTime = System.currentTimeMillis()
    println(s"Took: ${endTime - startTime}")

    // Answer: (837799,525)
    //
    // Calculate sequence and then take length: 39s
    // Memoized version of above: 51s
    // Calculate length without keeping the sequence itself: 12s
    // Memoized version of the above: 28s
  }

  //  def testFunction(x: Int): Unit = {
  //    println(s"Calling with $x")
  //    val result = slowFunction(x)
  //    println(s"Received ${result}")
  //  }
  //
  //  val slowFunction = memoize { (x: Int) => {
  //    println(s"Called with $x")
  //    x * 2
  //  }
  //  }
}
