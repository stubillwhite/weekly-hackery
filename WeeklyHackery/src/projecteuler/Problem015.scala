package projecteuler

object Problem015 {

  private def factorial(n: BigInt): BigInt = {
    Stream
      .range(BigInt(1), n)
      .foldLeft(BigInt(1))(_ * _)
  }

  def routesThroughGrid(cols: BigInt, rows: BigInt): BigInt = {
    factorial(cols + rows) / (factorial(cols) * factorial(rows))
  }

  def main(args: Array[String]): Unit = {
    println(routesThroughGrid(20, 20))
  }
}
