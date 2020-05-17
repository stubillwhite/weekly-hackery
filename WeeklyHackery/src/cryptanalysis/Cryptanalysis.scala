import cryptanalysis.internal.{CaesarCypher, CaesarCypherBreaker, CaesarCypherKey, LanguageModel}

object Cryptanalysis {

  def filter(text: String): String = {
    val validChars = "abcdefghijklmnopqrstuvwxyz ".toSet
    text.toLowerCase.filter(validChars.contains).mkString
  }

  def main(args: Array[String]): Unit = {
    val cypher = CaesarCypher(CaesarCypherKey(19))
    val plaintext = filter("""This is some sample text that we're going to encrypt. Longer text will be more likely to be decoded correctly.""")

    val cyphertext = cypher.encypher(plaintext)
    println(cyphertext)
    println(cypher.decypher(cyphertext))

    val sampleText = filter("""This is a sample piece of the original language. It won't be the same as the plaintext but it should be similar enough.""")
    val model = LanguageModel(sampleText.filter(_ != ' '))

    val breaker = CaesarCypherBreaker(model)
    for {
      probableKey <- breaker.probableKeys(cyphertext).take(3)
      probablePlaintext = CaesarCypher(probableKey.key).decypher(cyphertext)
    } yield {
      println(s"${probableKey} : ${probablePlaintext}")
    }
  }
}