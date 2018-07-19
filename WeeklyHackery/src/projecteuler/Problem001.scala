package projecteuler

object Problem001 {

  def multiplesOfThreeAndFive(): Stream[Int] = {
    Stream.from(1).filter(x => x % 3 == 0 || x % 5 == 0)
  }

  def main(args: Array[String]): Unit = {
    println(multiplesOfThreeAndFive().takeWhile(_ < 1000).sum)
  }
}
