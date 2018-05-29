package markvshaney.internal

import markvshaney.internal.MarkovTextGenerator.TextStream
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class TextStreamTrimmerTest extends FlatSpec with Matchers with MockitoSugar {

  behavior of "trim"

  it should "remove words until a capitalised word is encountered" in {
    // Given, When
    val trimmedText = TextStreamTrimmer().trim(textStream("a b c D e f"))

    trimmedText.head shouldBe "D"
  }

  it should "take words until a full stop is encountered beyond N words" in {
    // Given, When
    val trimmedText = TextStreamTrimmer(5).trim(textStream("a b c D e f. G h i j k l. m n o p"))

    trimmedText.mkString(" ") shouldBe "D e f. G h i j k l."
  }

  private def textStream(s: String): TextStream =
    s.split(" ").toStream
}
