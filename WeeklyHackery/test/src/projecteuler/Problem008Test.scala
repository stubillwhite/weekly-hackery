package projecteuler

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import projecteuler.Problem008.maximumWindowedProduct

class Problem008Test extends FlatSpec with Matchers with MockitoSugar {

  behavior of "maximumWindowedProduct"

  it should "return example product given example value" in {
    maximumWindowedProduct(4) should be (5832)
  }
}
