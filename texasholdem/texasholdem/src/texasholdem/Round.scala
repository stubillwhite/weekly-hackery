package texasholdem

import texasholdem.CombinatorialUtils._
import texasholdem.Deck.Card
import texasholdem.PokerHandClassifier.{PokerHand, classifyHand}

object Round {
  def apply(players: List[Player]): Round =
    new Round(
      players.map(x => x.id -> x).toMap,
      players.map(_.id),
      Deck().shuffle())

  type RoundPhase = (Round) => Round

  private val dealToPlayers: RoundPhase = (round: Round) => {
    def dealToPlayer(round: Round, playerId: String): Round = {
      val (newDeck, cards) = round.deck.deal(1)

      val player = round.playersById(playerId)
      val newPlayer = player.copy(holeCards = player.holeCards ++ cards)

      round.copy(playersById = round.playersById + (playerId -> newPlayer), round.playOrder, newDeck, round.communityCards)
    }

    round.playOrder.foldLeft(round)(dealToPlayer)
  }

  val burnCard: RoundPhase = (round: Round) => {
    val (newDeck, _) = round.deck.deal(1)
    round.copy(deck = newDeck)
  }

  private def dealToCommunityCards(round: Round, n: Int): Round = {
    val (newDeck, cards) = round.deck.deal(n)
    val newCommunityCards = round.communityCards ++ cards
    round.copy(deck = newDeck, communityCards = newCommunityCards)
  }

  val dealFlop: RoundPhase = (round: Round) => dealToCommunityCards(round, 3)

  val dealTurn: RoundPhase = (round: Round) => dealToCommunityCards(round, 1)

  val dealRiver: RoundPhase = (round: Round) => dealToCommunityCards(round, 1)

  val classifyHands: RoundPhase = (round: Round) => {
    def classifyPlayerHand(round: Round, playerId: String): Round = {
      val player = round.playersById(playerId)
      val newPlayer = player.copy(bestHand = Some(bestHand(round, playerId)))

      round.copy(playersById = round.playersById + (playerId -> newPlayer))
    }

    round.playOrder.foldLeft(round)(classifyPlayerHand)
  }

  def bestHand(round: Round,
               playerId: String): PokerHand = {
    val cards = round.playersById(playerId).holeCards ++ round.communityCards
    selectWithoutReplacement(5, cards)
      .map(classifyHand)
      .min
  }
}

case class Round(playersById: Map[String, Player],
                 playOrder: List[String],
                 deck: Deck,
                 communityCards: List[Card] = List()) {

  import Round._

  def play(): Round = {

    val playRound =
      dealToPlayers andThen
        dealToPlayers andThen
        burnCard andThen
        dealFlop andThen
        burnCard andThen
        dealTurn andThen
        burnCard andThen
        dealRiver andThen
        classifyHands

    playRound(this)
  }
}
