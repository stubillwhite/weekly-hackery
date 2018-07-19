package projecteuler

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import projecteuler.Problem005.leastCommonMultiple

class Problem005Test extends FlatSpec with Matchers with MockitoSugar {

  behavior of "leastCommonMultiple"

  it should "return example least common multiple given example ranges" in {
    leastCommonMultiple(1L until 10L: _*) should be (2520L)
  }
}
