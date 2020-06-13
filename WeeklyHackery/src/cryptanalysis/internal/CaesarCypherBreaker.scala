package cryptanalysis.internal

object CaesarCypherBreaker {
  def apply(language: Language): CaesarCypherBreaker =
    new CaesarCypherBreaker(language)
}

class CaesarCypherBreaker(language: Language) extends CypherBreaker[CaesarCypher, CaesarCypherKey] {

  override def probableKeys(sampletext: String, cyphertext: String): Seq[ProbableKey[CaesarCypherKey]] = {
    val sampletextFrequencies = FrequencyDistribution(language, sampletext)
    val cyphertextFrequencies = FrequencyDistribution(language, cyphertext)

    val keys = for {
      offset <- 0 to language.Letters.size
      rotatedStats = cyphertextFrequencies.rotate(offset)
      distance = sampletextFrequencies.distance(rotatedStats)
    } yield ProbableKey(CaesarCypherKey(language.Letters.size - offset), distance)

    keys.sortBy(_.distance)
  }
}
