package mastermind.internal

import mastermind.internal.Domain.{Code, Feedback}
import mastermind.internal.Domain.KeyPegs.{FullyCorrect, PartiallyCorrect}

object FeedbackCalculator {

  def calculateFeedback(code: Code, guess: Code): Feedback = {
    val fullyCorrect = code.zip(guess).count { case (c, g) => c == g }

    val (codeUnmatched, guessUnmatched) =
      code.zip(guess)
        .filterNot { case (c, g) => c == g }
        .unzip

    val partiallyCorrect = codeUnmatched.toSet.intersect(guessUnmatched.toSet).size

    val keyPegs = List.fill(fullyCorrect)(FullyCorrect) ++ List.fill(partiallyCorrect)(PartiallyCorrect)
    Feedback(keyPegs: _*)
  }
}
