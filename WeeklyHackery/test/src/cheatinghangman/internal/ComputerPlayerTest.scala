package cheatinghangman.internal

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class ComputerPlayerTest extends FlatSpec with Matchers with MockitoSugar {

  behavior of "guessCharacter"

  it should "consider only words with the correct length" in {
    // Given
    val player = ComputerPlayer(List("ab", "ac", "xxxx", "xxxx", "xxxx"))

    // When
    val guess = player.guessCharacter(word("- -"), "".toSet, 10)

    guess should be ('a')
  }

  it should "consider only words which match known letters" in {
    // Given
    val player = ComputerPlayer(List("abc", "abd", "xxb", "xxb", "xxb"))

    // When
    val guess = player.guessCharacter(word("- b -"), "b".toSet, 10)

    guess should be ('a')
  }

  it should "consider only words which do not match incorrect guesses" in {
    // Given
    val player = ComputerPlayer(List("aaa", "aaa", "xxb", "xxb", "xxb"))

    // When
    val guess = player.guessCharacter(word("- - -"), "x".toSet, 10)

    guess should be ('a')
  }

  it should "guess the letter occurring in the most words ignoring repetition within the word" in {
    // Given
    val player = ComputerPlayer(List("abc", "ade", "axx", "xxx"))

    // When
    val guess = player.guessCharacter(word("- - -"), "".toSet, 10)

    guess should be ('a')
  }

  private def word(s: String): List[Option[Char]] = {
    s.replaceAll(" ", "")
      .map(x => if (x == '-') None else Some(x)).toList
  }
}
