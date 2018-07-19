package projecteuler

object Problem004 {

  private def isPalindromicNumber(n: Long): Boolean = {
      n.toString == n.toString.reverse
  }

  private def products(a: Long, b: Long): Stream[Long] = {
    Stream.range(a, b + 1)
      .flatMap(x => Stream.range(a, b + 1).map(x * _))
  }

  def largestPalindrome(start: Long, end: Long): Long = {
    products(start, end).filter(isPalindromicNumber).max
  }

  def main(args: Array[String]): Unit = {
    println(largestPalindrome(100, 999))
  }
}
