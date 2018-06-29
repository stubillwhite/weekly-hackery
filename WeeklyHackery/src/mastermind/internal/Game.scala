package mastermind.internal

import mastermind.internal.Domain.KeyPegs.FullyCorrect
import mastermind.internal.Domain.{Code, Feedback, Round}

case class Game(codeMaker: CodeMaker,
                codeBreaker: CodeBreaker,
                code: Code,
                rounds: Vector[Round]) {

  private val fullyCorrect = Feedback(List.fill(code.length)(FullyCorrect): _*)

  def isWon(): Boolean = {
    rounds.nonEmpty && rounds.last.feedback.sameElements(fullyCorrect)
  }

  def isLost(): Boolean = {
    rounds.length == 10
  }
}
