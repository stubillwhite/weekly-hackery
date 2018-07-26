package projecteuler

import projecteuler.Problem007.isPrime

import scala.annotation.tailrec

object Problem010 {

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

  private def naturals: Stream[Long] = Stream.iterate(1L)(_ + 1L)

  def primes: Stream[Long] = naturals.drop(1).filter(isPrime)

  def sumOfPrimesBelow(n: Long): Long = {
    primes.takeWhile(_ < n).sum
  }

  def main(args: Array[String]): Unit = {
    println(sumOfPrimesBelow(2 * 1000 * 1000))
  }
}
