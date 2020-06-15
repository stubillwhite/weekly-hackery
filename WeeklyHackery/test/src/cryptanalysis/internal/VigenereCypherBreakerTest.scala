package cryptanalysis.internal

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class VigenereCypherBreakerTest extends FlatSpec with Matchers with MockitoSugar {

  private val sampleText = "abcdefghijklmnopqrstuvwxyz"

  behavior of "createSampledTexts"

  it should "return the text unchanged for step of 1" in {
    val cypher = VigenereCypherBreaker(null)

    val actual = cypher.createTextsForPeriod(sampleText, 1)

    actual should be(List(sampleText))
  }

  it should "return two sampled texts for step of 2" in {
    val cypher = VigenereCypherBreaker(null)

    val actual = cypher.createTextsForPeriod(sampleText, 2)

    actual should be(List(
      "acegikmoqsuwy",
      "bdfhjlnprtvxz"
    ))
  }

  it should "return three sampled texts for step of 3" in {
    val cypher = VigenereCypherBreaker(null)

    val actual = cypher.createTextsForPeriod(sampleText, 3)

    actual should be(List(
      "adgjmpsvy",
      "behknqtwz",
      "cfilorux"
    ))
  }

  behavior of "foo"

  it should "foo" in {
    val probableKeys = List(
      List(caesarKey(1, 1.0), caesarKey(6, 6.0), caesarKey(7, 7.0)),
      List(caesarKey(2, 2.0), caesarKey(5, 5.0), caesarKey(8, 8.0)),
      List(caesarKey(3, 3.0), caesarKey(4, 4.0), caesarKey(9, 9.0))
    )

    val expected = Stream(
      vigenereKey((1, 2, 3), 6.0),
      vigenereKey((1, 2, 4), 7.0),
      vigenereKey((1, 5, 4), 10.0),
      vigenereKey((6, 5, 4), 15.0),
      vigenereKey((7, 5, 4), 16.0),
      vigenereKey((7, 8, 4), 19.0),
      vigenereKey((7, 8, 9), 24.0)
    )

    val actual = VigenereCypherBreaker.vigenereKeySeq(probableKeys)

    actual should be (expected)
  }

  private def caesarKey(offset: Int, distance: Double): ProbableKey[CaesarCypherKey] = {
    ProbableKey(CaesarCypherKey(offset), distance)
  }

  private def vigenereKey(offsets: (Int, Int, Int), distance: Double): ProbableKey[VigenereCypherKey] = {
    offsets match {
      case (a, b, c) => ProbableKey(VigenereCypherKey(Seq(a, b, c)), distance)
    }
  }
}
