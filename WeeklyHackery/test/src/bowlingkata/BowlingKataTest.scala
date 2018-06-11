package bowlingkata

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import BowlingKata._

class BowlingKataTest extends FlatSpec with Matchers with MockitoSugar {

  behavior of "score game"

  it should "score a game of all gutter balls" in {
    scoreGame(List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)) should be(0)
  }

  it should "score a game of ten frames" in {
    scoreGame(List(1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2)) should be(30)
  }

  it should "score a frame with some pins left standing correctly" in {
    scoreGame(List(5, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)) should be(9)
  }

  it should "score a frame with a spare correctly" in {
    scoreGame(List(5, 5, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)) should be(17)
  }

  it should "score a frame with a strike correctly" in {
    scoreGame(List(10, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)) should be(20)
  }

  it should "score a game ending in a spare correctly" in {
    scoreGame(List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 5)) should be(15)
  }

  it should "score a game ending in a strike correctly" in {
    scoreGame(List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 2, 3)) should be(15)
  }

  it should "score a perfect game correctly" in {
    scoreGame(List(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10)) should be(300)
  }
}
