package texasholdem.internal

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class RoundTest extends FlatSpec with Matchers with MockitoSugar {

  behavior of "round.play"

  it should "deal to players and to community cards" in {
    // Given
    val players = List(
      Player("player-b"),
      Player("player-c"),
      Player("player-a"))

    // When
    val result = Round(players).play()

    // Then
    result.communityCards should have length 5
    result.deck.cards should have length (52 - (5 + 3 + (players.length * 2)))

    result.playOrder should contain theSameElementsInOrderAs List("player-b", "player-c", "player-a")
    result.playersById("player-a").holeCards should have length 2
    result.playersById("player-b").holeCards should have length 2
    result.playersById("player-c").holeCards should have length 2

    result.playersById("player-a").bestHand.isDefined should be(true)
    result.playersById("player-b").bestHand.isDefined should be(true)
    result.playersById("player-c").bestHand.isDefined should be(true)

  }
}
