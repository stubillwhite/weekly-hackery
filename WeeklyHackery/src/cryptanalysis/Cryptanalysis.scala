package cryptanalysis

import cryptanalysis.internal._

object Cryptanalysis {

//  private val plaintext = """This is some sample text that we're going to encrypt. Longer text will be more likely to be decoded correctly. Let's add a few more words to see if that works out a little better. Peter Piper picked a peck of pickled pepper."""
  private val plaintext = """yay! Successfully decoded, well done! now we can send sneaky messages in slack and nobody will be any the wiser hehehe"""

  private val sampletext = """This is a sample piece of the original language. It won't be the same as the plaintext but it should be similar enough. Let's add a few more words to see if that works out a little better. Peter Piper picked a peck of pickled pepper."""

  def main(args: Array[String]): Unit = {
    //        testCaesarCypher()
    testVigenereCypher()
  }

  private val language: Language = EnglishLanguage

  private def testVigenereCypher(): Unit = {
    val key = VigenereCypherKey(language, "recs")
    println(key)
    val cypher = VigenereCypher(language, key)

    val cyphertext = cypher.encypher(plaintext)
    println(cyphertext)
    println(cypher.decypher(cyphertext))

    val breaker = VigenereCypherBreaker(language)
    for {
      probableKey <- breaker.probableKeys(sampletext, cyphertext).take(10)
      probablePlaintext = VigenereCypher(language, probableKey.key).decypher(cyphertext)
    } yield {
      println(s"${probableKey} : ${probablePlaintext}")
    }
  }

  private def testCaesarCypher(): Unit = {
    val key = CaesarCypherKey(19)
    val cypher = CaesarCypher(language, key)

    val cyphertext = cypher.encypher(plaintext)
    println(cyphertext)
    println(cypher.decypher(cyphertext))

    val breaker = CaesarCypherBreaker(language)
    for {
      probableKey <- breaker.probableKeys(sampletext, cyphertext).take(3)
      probablePlaintext = CaesarCypher(language, probableKey.key).decypher(cyphertext)
    } yield {
      println(s"${probableKey} : ${probablePlaintext}")
    }
  }
}