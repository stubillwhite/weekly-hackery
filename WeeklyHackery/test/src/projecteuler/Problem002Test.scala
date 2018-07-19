package projecteuler

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import projecteuler.Problem002._

class Problem002Test extends FlatSpec with Matchers with MockitoSugar {

  behavior of "fibonacci"

  it should "return fibonacci sequence" in {
    fibonacci().take(10).toList should be (List(1, 2, 3, 5, 8, 13, 21, 34, 55, 89))
  }
}
