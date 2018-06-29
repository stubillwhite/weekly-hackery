package mastermind.internal

import mastermind.internal.Domain.{Code, Round}

trait CodeBreaker {

  def guess(previousGuesses: Vector[Round]): Code

}
