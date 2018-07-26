package projecteuler

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import projecteuler.Problem006.difference

class Problem006Test extends FlatSpec with Matchers with MockitoSugar {

  behavior of "difference"

  it should "return example difference given example value" in {
    difference(10) should be (2640L)
  }
}
