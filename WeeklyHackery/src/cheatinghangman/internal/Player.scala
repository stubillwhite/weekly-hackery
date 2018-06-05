package cheatinghangman.internal

trait Player {
  def guessCharacter(word: List[Option[Char]],
                     guessedLetters: Set[Char],
                     remainingLives: Int): Char
}
