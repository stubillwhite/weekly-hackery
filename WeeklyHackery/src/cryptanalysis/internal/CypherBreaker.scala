package cryptanalysis.internal

trait CypherBreaker[T <: Cypher[K], K <: Key] {
  def probableKeys(sampletext: String, cyphertext: String): Seq[ProbableKey[K]]
}
