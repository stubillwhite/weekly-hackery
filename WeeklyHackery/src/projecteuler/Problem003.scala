package projecteuler

import Math.{floor, sqrt}

object Problem003 {

  private def factors(n: Long): Set[Long] = {
    val limit = floor(sqrt(n.toDouble)).toLong + 1

    Stream.range(2, limit)
      .filter(n % _ == 0)
      .flatMap((x) => Array(x, n / x))
      .toSet
  }

  private def isPrime(n: Long): Boolean = {
    factors(n).isEmpty
  }

  def primeFactors(n: Long): Set[Long] = {
    factors(n).filter(isPrime)
  }

  def main(args: Array[String]): Unit = {
    println(primeFactors(13195L))
  }
}
