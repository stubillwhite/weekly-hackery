package markvshaney.internal

import markvshaney.internal.MarkovTextGenerator.{RandomNumberProvider, State, TextStream}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class MarkovTextGeneratorTest extends FlatSpec with Matchers with MockitoSugar {

  private val stubMaxRandomNumberProvider: RandomNumberProvider = new RandomNumberProvider {
    override def nextInt(n: Int): Int = n - 1
  }

  private val stubMinRandomNumberProvider: RandomNumberProvider = new RandomNumberProvider {
    override def nextInt(n: Int): Int = 0
  }

  behavior of "generate text"

  it should "continue until a termainal state" in {
    // Given
    val generator = MarkovTextGenerator(textStream("a b c"))

    // When
    val text = generator.generateText().mkString(" ")

    // Then
    text shouldBe "a b c"
  }

  it should "select transitions randomly" in {
    // Given
    val inputText = "a b c a b d"
    val startingState = initialState("a b")

    val minGenerator = MarkovTextGenerator(textStream(inputText), 2, stubMinRandomNumberProvider)
    val maxGenerator = MarkovTextGenerator(textStream(inputText), 2, stubMaxRandomNumberProvider)

    // When
    val textForMinPath = minGenerator.generateText(startingState).take(3).mkString(" ")
    val textForMaxPath = maxGenerator.generateText(startingState).take(3).mkString(" ")

    // Then
    List(textForMaxPath, textForMinPath) should contain theSameElementsAs List("a b c", "a b d")
  }

  private def textStream(s: String): TextStream =
    s.split(" ").toStream

  private def initialState(s: String): State =
    s.split(" ").toList

}
