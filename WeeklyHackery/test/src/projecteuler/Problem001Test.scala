package projecteuler

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import projecteuler.Problem001._

class Problem001Test extends FlatSpec with Matchers with MockitoSugar {

  behavior of "multiplesOfThreeAndFive"

  it should "return example output given the example input" in {
    multiplesOfThreeAndFive().takeWhile(_ < 10).sum should be (23)
  }
}
