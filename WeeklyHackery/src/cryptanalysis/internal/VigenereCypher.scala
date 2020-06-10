package cryptanalysis.internal

//case class VigenereCypherKey(offsets: List[Int]) extends Key
//
//object VigenereCypher {
//  def apply(): VigenereCypher = new VigenereCypher()
//}
//
//class VigenereCypher extends Cypher[VigenereCypherKey] {
//
//  override def encypher(language: Language, key: VigenereCypherKey, plaintext: String): String = {
//    applyOperation(language, key, plaintext, CaesarCypher().encypher)
//  }
//
//  override def decypher(language: Language, key: VigenereCypherKey, cyphertext: String): String = {
//    applyOperation(language, key, cyphertext, CaesarCypher().decypher)
//  }
//
//  private def applyOperation(language: Language, key: VigenereCypherKey, text: String, f: (Language, CaesarCypherKey, String) => String): String = {
//    def keyStream: Stream[CaesarCypherKey] = key.offsets.map(CaesarCypherKey).toStream #::: keyStream
//
//    text.toStream
//      .zip(keyStream)
//      .map { case (char, key) => f(language, key, char.toString) }
//      .mkString
//  }
//}
