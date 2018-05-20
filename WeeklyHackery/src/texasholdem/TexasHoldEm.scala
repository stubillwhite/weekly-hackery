package texasholdem

import texasholdem.internal.Display.displayState
import texasholdem.internal.{Player, Round}

object TexasHoldEm {

  def main(args: Array[String]): Unit = {
    val players = List(
      Player("player-a"),
      Player("player-b"),
      Player("player-c"),
      Player("player-d"))

    val completedRound = Round(players).play()

    displayState(completedRound)
  }
}
