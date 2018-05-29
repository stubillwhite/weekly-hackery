package markvshaney.internal

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class TokeniserTest extends FlatSpec with Matchers with MockitoSugar {

  behavior of "tokenise"

  it should "split on whitespace" in {
    // Given, When
    val tokens = Tokeniser.tokenise(text("a b c  d"))

    // Then
    tokens shouldBe List("a", "b", "c", "d")
  }

  it should "elide dialogue" in {
    // Given, When
    val tokens = Tokeniser.tokenise(text("a b \"dialogue in quotes\" c \"dialogue with newline\nin quotes\" d"))

    // Then
    tokens shouldBe List("a", "b", "c", "d")
  }

  private def text(s: String): Iterator[String] =
    s.split(" ").iterator
}
