package cryptanalysis.internal

import scala.collection.immutable.SortedMap

object LanguageModel {
  def apply(text: Seq[Char]): LanguageModel = {
    new LanguageModel(text.groupBy(identity).mapValues(_.size))
  }
}

class LanguageModel(letterFrequencies: Map[Char, Int]) {
  val frequencies: Map[Char, Double] = normaliseAndSort(letterFrequencies)

  private def normaliseAndSort(letterFrequencies: Map[Char, Int]): Map[Char, Double] = {
    val total = letterFrequencies.values.sum
    SortedMap[Char, Double]()(Ordering.by(letterFrequencies.get).reverse) ++ letterFrequencies.mapValues(_.toDouble / total)
  }
}
