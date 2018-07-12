package minesweeper

import mastermind.internal.Domain.{Code, CodePeg, Color}
import minesweeper.internal.Domain.Point
import minesweeper.internal.Game

import scala.annotation.tailrec

object Minesweeper {

  def main(args: Array[String]): Unit = {
    val game = Game.newEasyGame()
    playGame(game)
  }

  @tailrec
  def playGame(game: Game): Unit = {
    println()
    println(game.grid)

    if (game.isLost) {
      println("You lost!")
    }
    else if (game.isWon) {
      println("You won!")
    }
    else {
      print("Move: ")

      val guessString = Console.in.readLine().trim

      val Array(x, y) = guessString
        .split(" ")
        .map(x => x.toInt)
        .take(2)

      playGame(game.reveal(Point(x, y)))
    }
  }
}
