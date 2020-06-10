package cryptanalysis.internal

import cryptanalysis.internal.testcommon.TestLanguage
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class FrequencyDistributionTest extends FlatSpec with Matchers with MockitoSugar {

  private val language = TestLanguage

  behavior of "FrequencyDistribution"

  it should "calculate frequencies" in {
    // Given
    val distribution = FrequencyDistribution(language, "aaabbc")

    // When
    val actual = distribution.Frequencies

    // Then
    actual should be(Map('a' -> 3, 'b' -> 2, 'c' -> 1))
  }

  it should "calculate relative frequencies" in {
    // Given
    val distribution = FrequencyDistribution(language, "aaaabbcc")

    // When
    val actual = distribution.RelativeFrequencies

    // Then
    actual should be(Map('a' -> 0.5, 'b' -> 0.25, 'c' -> 0.25))
  }

  behavior of "rotate"

  it should "rotate the frequency distribution" in {
    // Given
    val distribution = FrequencyDistribution(language, "aaabbc")

    // When, Then
    distribution.rotate(0).Frequencies should be(Map('a' -> 3, 'b' -> 2, 'c' -> 1))
    distribution.rotate(1).Frequencies should be(Map('a' -> 1, 'b' -> 3, 'c' -> 2))
    distribution.rotate(2).Frequencies should be(Map('a' -> 2, 'b' -> 1, 'c' -> 3))
    distribution.rotate(3).Frequencies should be(Map('a' -> 3, 'b' -> 2, 'c' -> 1))
  }

  it should "rotate the relative frequency distribution" in {
    // Given
    val distribution = FrequencyDistribution(language, "aaabbc")

    // When, Then
    val aFreq = (100.0 * 3 / 6)
    val bFreq = (100.0 * 2 / 6)
    val cFreq = (100.0 * 1 / 6)
    distribution.rotate(0).RelativeFrequencies should be(Map('a' -> aFreq, 'b' -> bFreq, 'c' -> cFreq))
    distribution.rotate(1).RelativeFrequencies should be(Map('a' -> cFreq, 'b' -> aFreq, 'c' -> bFreq))
    distribution.rotate(2).RelativeFrequencies should be(Map('a' -> bFreq, 'b' -> cFreq, 'c' -> aFreq))
    distribution.rotate(3).RelativeFrequencies should be(Map('a' -> aFreq, 'b' -> bFreq, 'c' -> cFreq))
  }

  behavior of "distance"

  it should "return zero for the same distribution" in {
    // Given
    val distribution = FrequencyDistribution(language, "aaaabbcc")

    // When
    val actual = distribution.distance(distribution.rotate(0))

    // Then
    actual should be(0.0)
  }

  it should "return zero for the rotated distribution" in {
    // Given
    val distribution = FrequencyDistribution(language, "aaaabbcc")

    // When
    val actual = distribution.distance(distribution.rotate(1))

    // Then
    actual should be(0.0)
  }
}
