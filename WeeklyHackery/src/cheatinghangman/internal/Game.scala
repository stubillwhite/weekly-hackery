package cheatinghangman.internal

import cheatinghangman.internal.Display._

case class Game(word: String,
                candidateWords: List[String],
                guessedLetters: Set[Char],
                player: Player) {

  val remainingLives: Int =
    10 - guessedLetters.filterNot({ word.contains(_) }).size

  val isWon: Boolean =
    word.filterNot { guessedLetters.contains }.length == 0

  val isLost: Boolean =
    remainingLives == 0

  val knownLetters: List[Option[Char]] =
    revealedLetters(word, guessedLetters)

  def playRound(): Game = {

    display(this)

    val guess = player.guessCharacter(knownLetters, guessedLetters, remainingLives)
    val newCandidateWords = candidateWords.filterNot {_.contains(guess)}
    val newGuessedLetters = guessedLetters + guess

    if (word.contains(guess)) {
      if (newCandidateWords.isEmpty) {
        // Concede the letter and keep all alternatives, remembering that once the player has seen
        // the position of this letter then we can't change it
        val consistentCandidateWords = candidateWords.filter { revealedLetters(word, newGuessedLetters) == revealedLetters(_, newGuessedLetters) }

        Game(word, consistentCandidateWords, newGuessedLetters, player)
      }
      else {
        // Cheat and throw away words containing this letter
        Game(
          newCandidateWords.head,
          newCandidateWords.tail,
          newGuessedLetters,
          player)
      }
    }
    else {
      // Incorrect guess, but remember that we can't switch to a word containing this letter now
      Game(word, newCandidateWords, newGuessedLetters, player)
    }
  }

  private def revealedLetters(s: String, letters: Set[Char]): List[Option[Char]] =
    s.map(x => if (letters.contains(x)) Some(x) else None).toList
}
