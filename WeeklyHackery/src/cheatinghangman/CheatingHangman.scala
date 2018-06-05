package cheatinghangman

import cheatinghangman.internal.{ComputerPlayer, Display, Game, HumanPlayer}
import Display.display

import scala.annotation.tailrec
import scala.io.Source
import scala.util.Random

object CheatingHangman {

  def main(args: Array[String]): Unit = {

    val wordList = Random.shuffle(readWordList())

    val game = Game(
      wordList.head,
      wordList.filter(x => x.length == wordList.head.length),
      Set(),
      ComputerPlayer(wordList))

    playGame(game)
  }

  @tailrec
  private def playGame(game: Game): Unit = {
    if (game.isWon || game.isLost) {
      display(game)
    }
    else {
      playGame(game.playRound())
    }
  }

  private def readWordList(): List[String] = {
    val inputStream = getClass.getResourceAsStream("corncob_lowercase.txt")
    Source.fromInputStream(inputStream).getLines.toList
  }
}
