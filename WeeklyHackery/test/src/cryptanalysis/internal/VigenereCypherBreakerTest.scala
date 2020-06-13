package cryptanalysis.internal

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class VigenereCypherBreakerTest extends FlatSpec with Matchers with MockitoSugar {

  private val sampleText = "abcdefghijklmnopqrstuvwxyz"

  behavior of "createSampledTexts"

  it should "return the text unchanged for step of 1" in {
    val cypher = VigenereCypherBreaker(null)

    val actual = cypher.createSampledTexts(sampleText, 1)

    actual should be(List(sampleText))
  }

  it should "return two sampled texts for step of 2" in {
    val cypher = VigenereCypherBreaker(null)

    val actual = cypher.createSampledTexts(sampleText, 2)

    actual should be(List(
      "acegikmoqsuwy",
      "bdfhjlnprtvxz"
    ))
  }

  it should "return three sampled texts for step of 3" in {
    val cypher = VigenereCypherBreaker(null)

    val actual = cypher.createSampledTexts(sampleText, 3)

    actual should be(List(
      "adgjmpsvy",
      "behknqtwz",
      "cfilorux"
    ))
  }
}
