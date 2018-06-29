package mastermind.internal

import mastermind.internal.Domain._

class HumanCodeBreaker extends CodeBreaker {

  override def guess(previousGuesses: Vector[Round]): Code = {
    print("Guess: ")

    val guessString = Console.in.readLine().trim

    val codePegs = guessString
      .split(" ")
      .map(x => CodePeg(Color.withName(x)))

    Code(codePegs: _*)
  }
}
