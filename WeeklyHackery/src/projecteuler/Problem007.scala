package projecteuler

import scala.annotation.tailrec

object Problem007 {

  private def isPrime(n: Long): Boolean = {
    val limit = Math.floor(Math.sqrt(n.toDouble)).toLong

    @tailrec
    def iter(i: Long): Boolean = {
      if (i > limit) true
      else if ((n % i) == 0) false
      else iter(i + 1)
    }

    iter(2)
  }

  def naturals: Stream[Long] = Stream.iterate(1L)(_ + 1)

  def primes: Stream[Long] = naturals.filter(isPrime)

  def main(args: Array[String]): Unit = {
    println(primes(10001))
  }
}
