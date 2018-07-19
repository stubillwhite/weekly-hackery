package projecteuler

import scala.annotation.tailrec

object Problem005 {

  @tailrec
  private def greatestCommonDivisor(a: Long, b: Long): Long = {
    if (b == 0) a
    else greatestCommonDivisor(b, a % b)
  }

  private def leastCommonMultiple(a: Long, b: Long): Long = {
    (a * b) / greatestCommonDivisor(a, b)
  }

  def leastCommonMultiple(as: Long*): Long = {
    as.foldLeft(1L)((acc, a) => leastCommonMultiple(acc, a))
  }

  def main(args: Array[String]): Unit = {
    println(leastCommonMultiple(1L until 20L: _*))
  }
}
