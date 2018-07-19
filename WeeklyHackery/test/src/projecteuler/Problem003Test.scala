package projecteuler

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import projecteuler.Problem003._

class Problem003Test extends FlatSpec with Matchers with MockitoSugar {

  behavior of "primeFactors"

  it should "return example prime factors given example number" in {
    primeFactors(13195) should be(Set(5, 7, 13, 29))
  }

  it should "return include the square root as a prime factor" in {
    primeFactors(25) should be(Set(5))
  }
}
