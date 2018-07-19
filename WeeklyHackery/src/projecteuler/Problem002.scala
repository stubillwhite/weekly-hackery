package projecteuler

object Problem002 {

  def fibonacci(): Stream[Int] = {
    Stream.iterate((1, 2)) { case (a, b) => (b, a + b) }.map(_._1)
  }

  def main(args: Array[String]): Unit = {
    println(fibonacci().takeWhile(_ < 4000000).filter(_ % 2 == 0).sum)
  }
}
