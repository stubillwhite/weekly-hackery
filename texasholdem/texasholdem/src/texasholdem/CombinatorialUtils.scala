package texasholdem

object CombinatorialUtils {

  def selectWithoutReplacement[A](n: Int, items: List[A]): List[List[A]] = {
    if (n == 0) List(List())
    else if (n == items.length) List(items)
    else selectWithoutReplacement(n - 1, items.tail).map(items.head :: _) ++ selectWithoutReplacement(n, items.tail)
  }
}
