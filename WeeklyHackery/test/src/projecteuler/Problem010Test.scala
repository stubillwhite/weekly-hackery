package projecteuler

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import projecteuler.Problem010.sumOfPrimesBelow

class Problem010Test extends FlatSpec with Matchers with MockitoSugar {

  behavior of "sumOfPrimesBelow"

  it should "return example result given example value" in {
    sumOfPrimesBelow(10) should be (17)
  }
}
