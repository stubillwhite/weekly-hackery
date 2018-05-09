import texasholdem.{Player, PlayerHand, Round}

object TexasHoldEm {

  def main(args: Array[String]): Unit = {
    val players = List(
      Player("player-a", PlayerHand()),
      Player("player-b", PlayerHand()),
      Player("player-c", PlayerHand()),
      Player("player-d", PlayerHand()))

    val round = Round(players)

    round.play()
  }
}
