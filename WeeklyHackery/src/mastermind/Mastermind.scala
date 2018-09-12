package mastermind

import mastermind.internal.Domain.KeyPegs._
import mastermind.internal.Domain.{Code, Feedback, Round}
import mastermind.internal.FeedbackCalculator.calculateFeedback
import mastermind.internal.{CodeMaker, Game, HumanCodeBreaker}

import scala.annotation.tailrec

object Mastermind {

  def main(args: Array[String]): Unit = {
    val codeMaker = CodeMaker()
    val codeBreaker = new HumanCodeBreaker()
    val code = codeMaker.createCode()

    playGame(Game(codeMaker, codeBreaker, code, Vector()))

    println("Done")
  }

  @tailrec
  private def playGame(game: Game): Unit = {
    display(game)

    if (game.isWon()) {
      println("You win!")
    }
    else if (game.isLost()) {
      println("You lose!")
    }
    else {
      val guess = game.codeBreaker.guess(game.rounds)
      val feedback = calculateFeedback(game.code, guess)

      playGame(game.copy(rounds = game.rounds :+ Round(guess, feedback)))
    }
  }

  def display(game: Game): Unit = {
    println
    println(s"Secret code: ${codeToString(game.code)}")
    game.rounds.foreach(round => {
      println(s"${codeToString(round.guess)} | ${feedbackToString(calculateFeedback(game.code, round.guess))}")
    })
  }

  def feedbackToString(feedback: Feedback): String = {
    feedback
      .map(_.color.toString)
      .mkString(" ")
  }

  private def codeToString(code: Code): String = {
    code
      .map(_.color.toString)
      .mkString(" ")
  }
}
