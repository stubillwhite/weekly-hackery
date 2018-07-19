package projecteuler

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import projecteuler.Problem004.largestPalindrome

class Problem004Test extends FlatSpec with Matchers with MockitoSugar {

  behavior of "largestPalindrome"

  it should "return example palindrome given example ranges" in {
    largestPalindrome(10, 99) should be (9009)
  }
}
