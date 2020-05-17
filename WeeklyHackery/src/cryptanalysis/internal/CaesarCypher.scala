package cryptanalysis.internal

case class CaesarCypherKey(offset: Int) extends Key

object CaesarCypher {
  def apply(key: CaesarCypherKey): CaesarCypher = new CaesarCypher(key)
}

class CaesarCypher(key: CaesarCypherKey) extends Cypher[CaesarCypherKey] {
  override def encypher(plaintext: String): String = {
    plaintext.map(shiftChar(_, key.offset)).mkString
  }

  override def decypher(cyphertext: String): String = {
    cyphertext.map(shiftChar(_, -key.offset)).mkString
  }

  private def shiftChar(ch: Char, shiftValue: Int): Char = {
    if (ch.isLetter) {
      val startCode = 'a'.toInt
      val shiftedCode = (ch.toInt - startCode + shiftValue + 26) % 26
      (startCode + shiftedCode).toChar
    }
    else {
      ch
    }
  }
}
