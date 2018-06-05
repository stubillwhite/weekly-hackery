package cheatinghangman.internal

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class GameTest extends FlatSpec with Matchers with MockitoSugar {

  behavior of "playRound"

  it should "remove candidate words on an incorrect guess" in {
    // Given
    val game = Game("cat", List("car", "cow", "dog", "pig"), Set(), stubPlayerGuessing('o'))

    // When
    val actual = game.playRound()

    // Then
    actual should be (game.copy(candidateWords = List("car", "pig"), guessedLetters = "o".toSet))
  }

  it should "concede a letter on a correct guess if changing provides no benefit" in {
    // Given
    val game = Game("cat", List("car"), Set(), stubPlayerGuessing('c'))

    // When
    val actual = game.playRound()

    // Then
    actual should be (game.copy(guessedLetters = "c".toSet))
    actual.knownLetters should be (knownLetters("c - -"))
  }

  it should "concede a letter on a correct guess and remove candidates whose letter positions are not consistent with the conceded letters" in {
    // Given
    val game = Game("faff", List("farf", "fuff"), "".toSet, stubPlayerGuessing('f'))

    // When
    val actual = game.playRound()

    // Then
    actual should be (game.copy(candidateWords = List("fuff"), guessedLetters = "f".toSet))
    actual.knownLetters should be (knownLetters("f - f f"))
  }

  it should "concede a letter on a correct guess if there are no alternatives" in {
    // Given
    val game = Game("cat", List(), Set(), stubPlayerGuessing('c'))

    // When
    val actual = game.playRound()

    // Then
    actual should be (game.copy(guessedLetters = "c".toSet))
    actual.knownLetters should be (knownLetters("c - -"))
  }

  it should "switch words on a correct guess if there are viable alternatives" in {
    // Given
    val game = Game("cat", List("car"), Set(), stubPlayerGuessing('t'))

    // When
    val actual = game.playRound()

    // Then
    actual should be (game.copy(word = "car", candidateWords = List(), guessedLetters = Set('t')))
  }

  it should "mark the game as won if all letters are guessed" in {
    // Given
    val game = Game("cat", List(), "ca".toSet, stubPlayerGuessing('t'))

    // When
    val actual = game.playRound()

    // Then
    actual should be (game.copy(guessedLetters = "cat".toSet))
    actual.isWon should be (true)
    actual.knownLetters should be (knownLetters("c a t"))
  }

  it should "mark the game as lost if all lives are used" in {
    // Given
    val game = Game("zzz", List(), "abcdefghi".toSet, stubPlayerGuessing('j'))

    // When
    val actual = game.playRound()

    // Then
    actual.isLost should be (true)
  }

  private def knownLetters(s: String): List[Option[Char]] = {
    s.replaceAll(" ", "")
      .map(x => if (x == '-') None else Some(x)).toList
  }

  private def stubPlayerGuessing(c: Char): Player = {
    new Player {
      override def guessCharacter(word: List[Option[Char]],
                                  guessedLetters: Set[Char],
                                  remainingLives: Int): Char = c
    }
  }
}
