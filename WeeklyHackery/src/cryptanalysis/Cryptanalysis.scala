import cryptanalysis.internal.{CaesarCypher, CaesarCypherBreaker, CaesarCypherKey, LanguageModel, VigenereCypher, VigenereCypherBreaker, VigenereCypherKey}

object Cryptanalysis {

  def filter(text: String): String = {
    val validChars = "abcdefghijklmnopqrstuvwxyz ".toSet
    text.toLowerCase.filter(validChars.contains).mkString
  }

  def main(args: Array[String]): Unit = {
    //    testCaesarCypher
        testVigenereCypher
  }

  private def testVigenereCypher(): Unit = {
    val key = VigenereCypherKey(List(1, 2, 3, 4))
    val cypher = VigenereCypher

    val plaintext = filter("""This is some sample text that we're going to encrypt. Longer text will be more likely to be decoded correctly. Let's add a few more words to see if that works out a little better.""")

    val cyphertext = cypher().encypher(key, plaintext)
    println(cyphertext)
    println(cypher().decypher(key, cyphertext))

    val sampleText = filter("""This is a sample piece of the original language. It won't be the same as the plaintext but it should be similar enough. Let's add a few more words to see if that works out a little better.""")
    val model = LanguageModel(sampleText.filter(_ != ' '))

    val breaker = VigenereCypherBreaker(model)
    for {
      probableKey <- breaker.probableKeys(cyphertext).take(3)
      probablePlaintext = VigenereCypher().decypher(probableKey.key, cyphertext)
    } yield {
      println(s"${probableKey} : ${probablePlaintext}")
    }
  }

  private def testCaesarCypher(): Unit = {
    val key = CaesarCypherKey(19)
    val cypher = CaesarCypher()
    val plaintext = filter("""This is some sample text that we're going to encrypt. Longer text will be more likely to be decoded correctly.""")

    val cyphertext = cypher.encypher(key, plaintext)
    println(cyphertext)
    println(cypher.decypher(key, cyphertext))

    val sampleText = filter("""This is a sample piece of the original language. It won't be the same as the plaintext but it should be similar enough.""")
    val model = LanguageModel(sampleText.filter(_ != ' '))

    val breaker = CaesarCypherBreaker(model)
    for {
      probableKey <- breaker.probableKeys(cyphertext).take(3)
      probablePlaintext = CaesarCypher().decypher(probableKey.key, cyphertext)
    } yield {
      println(s"${probableKey} : ${probablePlaintext}")
    }
  }
}