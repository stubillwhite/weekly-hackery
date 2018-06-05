package cheatinghangman.internal

object HumanPlayer {
  def apply(): HumanPlayer = new HumanPlayer()
}

class HumanPlayer extends Player {
  override def guessCharacter(word: List[Option[Char]],
                              guessedLetters: Set[Char],
                              remainingLives: Int): Char = {
    print("Guess a letter: ")
    Console.in.readLine().trim.head
  }
}
