package cryptanalysis.internal

object CaesarCypherBreaker {
  def apply(language: Language): CaesarCypherBreaker =
    new CaesarCypherBreaker(language)
}

class CaesarCypherBreaker(language: Language) extends CypherBreaker[CaesarCypher, CaesarCypherKey] {

  override def probableKeys(sampletext: String, cyphertext: String): Seq[ProbableKey[CaesarCypherKey]] = {
    val sampletextFrequencies = FrequencyDistribution(language, sampletext)
    val cyphertextFrequencies = FrequencyDistribution(language, cyphertext)

    val languageSize = language.Letters.size

    val keys = for {
      offset <- language.Letters.indices
      rotatedStats = cyphertextFrequencies.rotate(offset)
      distance = sampletextFrequencies.distance(rotatedStats)
    } yield {
      ProbableKey(CaesarCypherKey((languageSize - offset) % languageSize), distance)
    }

    keys.sortBy(_.distance)
  }
}
