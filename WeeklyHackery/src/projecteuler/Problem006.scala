package projecteuler

object Problem006 {

  private def square(n: Long): Long = {
    n * n
  }

  private def squareOfSumUpTo(n: Long): Long = {
    val sum = (n * (n + 1)) / 2
    square(sum)
  }

  private def sumOfSquaresUpTo(n: Long): Long = {
    (n * (n + 1) * (2 * n + 1)) / 6
  }

  def difference(n: Long): Long = {
    squareOfSumUpTo(n) -
      sumOfSquaresUpTo(n)
  }

  def main(args: Array[String]): Unit = {
    println(difference(100))
  }
}
