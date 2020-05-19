package cryptanalysis.internal

case class CaesarCypherKey(offset: Int) extends Key

object CaesarCypher {
  def apply(): CaesarCypher = new CaesarCypher()
}

class CaesarCypher extends Cypher[CaesarCypherKey] {

  override def encypher(key: CaesarCypherKey, plaintext: String): String = {
    plaintext.map(shiftChar(_, key.offset)).mkString
  }

  override def decypher(key: CaesarCypherKey, cyphertext: String): String = {
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
