package projecteuler

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import projecteuler.Problem012._

class Problem012Test extends FlatSpec with Matchers with MockitoSugar {

  behavior of "triangularNumbers"

  it should "return triangular numbers" in {
    triangularNumbers.take(10) should be(List(1, 3, 6, 10, 15, 21, 28, 36, 45, 55))
  }

  behavior of "divisors"

  it should "return divisors of example numbers" in {
    divisors(28) should be (Set(1, 2, 4, 7, 14, 28))
  }
}
