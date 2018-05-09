package texasholdem

object Round {
  def apply(players: List[Player]): Round =
    new Round(
      players.map(x => x.id -> x).toMap,
      players.map(_.id),
      Deck().shuffle(),
      CommunityCards())

  type RoundPhase = (Round) => Round

  val dealToPlayers: RoundPhase = (round: Round) => {
    def dealToPlayer(round: Round, playerId: String): Round = {
      val (newDeck, cards) = round.deck.deal(1)

      val player = round.playersById(playerId)
      val oldPlayerHand = player.playerHand

      val newHand = oldPlayerHand.copy(holeCards = oldPlayerHand.holeCards ++ cards)
      val newPlayer = player.copy(playerHand = newHand)

      new Round(round.playersById + (playerId -> newPlayer), round.playOrder, newDeck, round.communityCards)
    }

    round.playOrder.foldLeft(round)(dealToPlayer)
  }

  val displayState: RoundPhase = (round: Round) => {
    println(
      s"""
         |Community cards:
         |  Flop:  ${round.communityCards.flop.mkString(" ")}
         |  Turn:  ${round.communityCards.turn.mkString(" ")}
         |  River: ${round.communityCards.river.mkString(" ")}
         |
          |Player hole cards:""".stripMargin)
    round.playOrder.foreach(playerId => {
      println(s"""  ${playerId}: ${round.playersById(playerId).playerHand.holeCards.mkString(" ")}""")
    })
    round
  }

  val burnCard: RoundPhase = (round: Round) => {
    val (newDeck, _) = round.deck.deal(1)
    new Round(round.playersById, round.playOrder, newDeck, round.communityCards)
  }

  val dealFlop: RoundPhase = (round: Round) => {
    val (newDeck, cards) = round.deck.deal(3)
    val newCommunityCards = round.communityCards.copy(flop = cards)
    new Round(round.playersById, round.playOrder, newDeck, newCommunityCards)
  }

  val dealTurn: RoundPhase = (round: Round) => {
    val (newDeck, cards) = round.deck.deal(1)
    val newCommunityCards = round.communityCards.copy(turn = cards)
    new Round(round.playersById, round.playOrder, newDeck, newCommunityCards)
  }

  val dealRiver: RoundPhase = (round: Round) => {
    val (newDeck, cards) = round.deck.deal(1)
    val newCommunityCards = round.communityCards.copy(river = cards)
    new Round(round.playersById, round.playOrder, newDeck, newCommunityCards)
  }
}

class Round(val playersById: Map[String, Player],
            val playOrder: List[String],
            val deck: Deck,
            val communityCards: CommunityCards) {

  def play(): Round = {
    import Round._

    val playRound =
      dealToPlayers andThen
        dealToPlayers andThen
        burnCard andThen
        dealFlop andThen
        burnCard andThen
        dealTurn andThen
        burnCard andThen
        dealRiver andThen
        displayState

    playRound(this)
  }
}
