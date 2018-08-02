package projecteuler

import java.lang.Math.{floor, sqrt}

object Problem012 {

  def triangularNumbers: Stream[Long] = Stream.iterate((1L, 2L))({ case (a, b) => (a+b, b+1) } ).map(_._1)

  def divisors(n: Long): Set[Long] = {
    val limit = floor(sqrt(n.toDouble)).toLong + 1

    Stream.range(1L, limit)
      .filter(n % _ == 0)
      .flatMap((x) => Array(x, n / x))
      .toSet
  }

  def main(args: Array[String]): Unit = {
    val result =
      triangularNumbers
        .map(x => (x, divisors(x).size))
        .dropWhile(_._2 <= 500)
        .head

    println(result)
  }
}
