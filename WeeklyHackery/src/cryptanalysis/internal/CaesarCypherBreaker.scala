package cryptanalysis.internal

case class ProbableKey[K <: Key](key: K, distance: Double)

trait CypherBreaker[T <: Cypher[K], K <: Key] {
  def probableKeys(cyphertext: String): Seq[ProbableKey[K]]
}

object CaesarCypherBreaker {
  def apply(languageModel: LanguageModel): CaesarCypherBreaker = new CaesarCypherBreaker(languageModel)
}

class CaesarCypherBreaker(languageModel: LanguageModel) extends CypherBreaker[CaesarCypher, CaesarCypherKey] {

  override def probableKeys(cyphertext: String): Seq[ProbableKey[CaesarCypherKey]] = {
    val keys = for {
      offset <- 0 to 25
      cypher = CaesarCypher(CaesarCypherKey(offset))
      decypheredLanguageModel = LanguageModel(cypher.decypher(cyphertext))
      distance = calculateDistance(languageModel, decypheredLanguageModel)
    } yield ProbableKey(CaesarCypherKey(offset), distance)

    keys.sortBy(_.distance)
  }

  private def calculateDistance(a: LanguageModel, b: LanguageModel): Double = {
    a.frequencies.keys
      .map( (k: Char) => Math.pow(a.frequencies.getOrElse(k, 0.0) - b.frequencies.getOrElse(k, 0.0), 2))
      .sum
  }
}
