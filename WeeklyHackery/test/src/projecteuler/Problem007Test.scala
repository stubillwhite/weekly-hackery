package projecteuler

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import projecteuler.Problem007.primes

class Problem007Test extends FlatSpec with Matchers with MockitoSugar {

  behavior of "primes"

  it should "return example primes given example value" in {
    primes(6) should be (13L)
  }
}
