package texasholdem

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class RoundTest extends FlatSpec with Matchers with MockitoSugar {

  behavior of "round.play"

  it should "deal to players and to community cards" in {
    // Given
    val players = List(
      Player("player-b", PlayerHand()),
      Player("player-c", PlayerHand()),
      Player("player-a", PlayerHand()))

    // When
    val result = Round(players).play()

    // Then
    result.communityCards.flop should have length 3
    result.communityCards.turn should have length 1
    result.communityCards.river should have length 1
    result.deck.cards should have length (52 - (5 + 3 + (3 * 2)))

    result.playOrder should contain theSameElementsInOrderAs List("player-b", "player-c", "player-a")
    result.playersById("player-a").playerHand.holeCards should have length 2
    result.playersById("player-b").playerHand.holeCards should have length 2
    result.playersById("player-c").playerHand.holeCards should have length 2
  }
}
