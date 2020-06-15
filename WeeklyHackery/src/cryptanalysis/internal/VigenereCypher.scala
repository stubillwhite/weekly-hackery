package cryptanalysis.internal

object VigenereCypherKey {
  def apply(language: Language, keyword: String): VigenereCypherKey = {
    val offsets = language.toLanguage(keyword).map(language.Letters.indexOf)
    new VigenereCypherKey(offsets)
  }
}

case class VigenereCypherKey(offsets: Seq[Int]) extends Key

object VigenereCypher {
  def apply(language: Language, key: VigenereCypherKey): VigenereCypher = new VigenereCypher(language, key)
}

class VigenereCypher(language: Language, key: VigenereCypherKey) extends Cypher[VigenereCypherKey] {

  private def cypherStream: Stream[CaesarCypher] =
    key.offsets.map(offset => CaesarCypher(language, CaesarCypherKey(offset))).toStream #::: cypherStream

  override def encypher(plaintext: String): String = {
    language.toLanguage(plaintext)
      .toStream
      .zip(cypherStream)
      .map { case (char, cypher) => cypher.encypher(char.toString) }
      .mkString
  }

  override def decypher(cyphertext: String): String = {
    language.toLanguage(cyphertext)
      .toStream
      .zip(cypherStream)
      .map { case (char, cypher) => cypher.decypher(char.toString) }
      .mkString
  }
}
