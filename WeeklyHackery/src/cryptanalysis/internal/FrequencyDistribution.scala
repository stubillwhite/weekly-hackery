package cryptanalysis.internal

import scala.collection.immutable.SortedMap

object FrequencyDistribution {

  def apply(language: Language, text: Seq[Char]): FrequencyDistribution = {
    val frequencies = buildFrequencies(language, text)
    val relativeFrequencies = buildRelativeFrequencies(frequencies)

    new FrequencyDistribution(language, frequencies, relativeFrequencies)
  }

  private def buildFrequencies(language: Language, text: Seq[Char]): Map[Char, Int] = {
    val defaultCounts = (for {ch <- language.Letters} yield (ch -> 0)).toMap
    val textCounts = language
      .toLanguage(text)
      .filter(language.Letters.contains)
      .groupBy(identity)
      .mapValues(_.size)

    SortedMap[Char, Int]()(Ordering.by(language.Letters.indexOf)) ++ defaultCounts ++ textCounts
  }

  private def buildRelativeFrequencies(frequencies: Map[Char, Int]): Map[Char, Double] = {
    val total = frequencies.values.sum
    frequencies.mapValues(x => (100.0 * x.toDouble) / total)
  }
}

class FrequencyDistribution(language: Language, frequencies: Map[Char, Int], relativeFrequencies: Map[Char, Double]) {

  val Frequencies: Map[Char, Int] = frequencies

  val RelativeFrequencies: Map[Char, Double] = relativeFrequencies

  def rotate(offset: Int): FrequencyDistribution = {
    def shifted: Stream[Char] = language.Letters.toStream #::: shifted

    val letterMappings = language.Letters.zip(shifted.drop(offset)).toMap

    val newFrequencies = SortedMap[Char, Int]()(Ordering.by(language.Letters.indexOf)) ++ Frequencies.map { case (ch, count) => letterMappings(ch) -> count }
    val newRelativeFrequencies = SortedMap[Char, Double]()(Ordering.by(language.Letters.indexOf)) ++ RelativeFrequencies.map { case (ch, count) => letterMappings(ch) -> count }

    new FrequencyDistribution(language, newFrequencies, newRelativeFrequencies)
  }

  //  http://practicalcryptography.com/cryptanalysis/text-characterisation/chi-squared-statistic/

  def distance(other: FrequencyDistribution): Double = {
    val thisTotal = Frequencies.values.sum
    val otherTotal = Frequencies.values.sum

    def chiSquared(k: Char): Double = {
      val observed = other.Frequencies(k)
      val expected = RelativeFrequencies(k) / thisTotal * otherTotal
      val distance = Math.pow(observed - expected, 2) / expected
      distance
    }

    RelativeFrequencies.keys
      .filter(k => RelativeFrequencies(k) != 0)
      .map(chiSquared)
      .sum
  }
}