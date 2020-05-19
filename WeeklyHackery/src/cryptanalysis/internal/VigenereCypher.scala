package cryptanalysis.internal

case class VigenereCypherKey(offsets: List[Int]) extends Key

object VigenereCypher {
  def apply(): VigenereCypher = new VigenereCypher()
}

class VigenereCypher extends Cypher[VigenereCypherKey] {

  override def encypher(key: VigenereCypherKey, plaintext: String): String = {
    applyOperation(key, plaintext, CaesarCypher().encypher)
  }

  override def decypher(key: VigenereCypherKey, cyphertext: String): String = {
    applyOperation(key, cyphertext, CaesarCypher().decypher)
  }

  private def applyOperation(key: VigenereCypherKey, text: String, f: (CaesarCypherKey, String) => String): String = {
    def keyStream: Stream[CaesarCypherKey] = key.offsets.map(CaesarCypherKey).toStream #::: keyStream

    text.toStream
      .zip(keyStream)
      .map { case (char, key) => f(key, char.toString) }
      .mkString
  }
}
