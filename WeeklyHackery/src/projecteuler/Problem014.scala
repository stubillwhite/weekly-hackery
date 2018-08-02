package projecteuler


object Problem014 {

  implicit class TakeUntilStreamWrapper[T](list: Stream[T]) {
    def takeUntil(predicate: T => Boolean): Stream[T] = {
      list.span(!predicate(_)) match {
        case (head, tail) => head append tail.take(1)
      }
    }
  }

  private def nextNumber(n: Long): Long = {
    if (n % 2 == 0) n / 2
    else 3 * n + 1
  }

  def collatz(n: Long): Seq[Long] =
    Stream.iterate(n)(nextNumber).takeUntil { _ == 1 }

  def main(args: Array[String]): Unit = {
    val result = (1L to 1000000L)
      .map(x => (x, collatz(x).length))
      .maxBy(_._2)

    println(result)
  }
}
