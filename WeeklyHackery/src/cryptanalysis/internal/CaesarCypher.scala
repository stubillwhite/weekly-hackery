package cryptanalysis.internal

final case class CaesarCypherKey(offset: Int) extends Key

object CaesarCypher {
  def apply(language: Language, key: CaesarCypherKey): CaesarCypher =
    new CaesarCypher(language, key)
}

class CaesarCypher(val language: Language,
                   val key: CaesarCypherKey) extends Cypher[CaesarCypherKey] {

  val encypherTable: Map[Char, Char] = createCypherTable(key.offset)
  val decypherTable: Map[Char, Char] = createCypherTable(-key.offset)

  override def encypher(plaintext: String): String = {
    language.toLanguage(plaintext)
      .map(encypherTable)
      .mkString
  }

  override def decypher(cyphertext: String): String = {
    language.toLanguage(cyphertext)
      .map(decypherTable)
      .mkString
  }

  private def createCypherTable(offset: Int): Map[Char, Char] = {
    def shifted: Stream[Char] = language.Letters.toStream #::: shifted
    val size = language.Letters.size

    val letterMappings = language.Letters.zip(shifted.drop((offset + size) % size))

    letterMappings.toMap
  }
}
