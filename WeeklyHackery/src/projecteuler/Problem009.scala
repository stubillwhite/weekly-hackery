package projecteuler

object Problem009 {

  private def square(x: Long): Long = {
    x * x
  }

  def pythagoreanTriplet(a: Long, b: Long, c: Long): Boolean = {
    ((a < b) && (b < c)) && (square(a) + square(b) == square(c))
  }

  def main(args: Array[String]): Unit = {

    val triplets = for {
      a <- 1L to 1000L
      b <- (a + 1L) to 1000L
      c <- (b + 1L) to 1000L
      if a + b + c == 1000L
    } yield {
      (a, b, c)
    }

    triplets.find { case ((a, b, c)) => pythagoreanTriplet(a, b, c) } match {
      case Some((a, b, c)) =>
        println(s"$a * $b * $c = ${a * b * c}")

      case None =>
        println("Failed to find")
    }
  }
}
