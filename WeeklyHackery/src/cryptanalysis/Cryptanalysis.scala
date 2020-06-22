package cryptanalysis

import cryptanalysis.internal._

object Cryptanalysis {

  private val language: Language = EnglishLanguage
  private val plaintext = """This is some sample text that we're going to encrypt. The text needs to be a decent enough length that the frequency distribution is going to be roughly like the rest of the language. The more text we provide, the more likely it is that we'll get a good decryption."""
  private val sampletext = """This is a sample piece of the original language. It won't be the same as the plaintext but it should be similar enough that the letter distributions end up somewhat close. Let's add a few more words just so we've got a good mix."""

  def main(args: Array[String]): Unit = {
    println("CaesarCypher")
    testCypher((k: CaesarCypherKey) => CaesarCypher(language, k), CaesarCypherKey(3), CaesarCypherBreaker(language))

    println("VigenereCypher")
    testCypher((k: VigenereCypherKey) => VigenereCypher(language, k), VigenereCypherKey(language, "hulk"), VigenereCypherBreaker(language))
  }

  private def testCypher[C <: Cypher[K], K <: Key, B <: CypherBreaker[C, K]](cypherFactory: (K) => C, key: K, breaker: B): Unit = {
    val cypher = cypherFactory(key)
    val cyphertext = cypher.encypher(plaintext)

    println(s"Key:        ${key}")
    println(s"Plaintext:  ${plaintext}")
    println(s"Encyphered: ${cyphertext}")
    println(s"Decyphered: ${cypher.decypher(cyphertext)}")
    println()

    println("Attempting to break")
    val probableKeys = breaker.probableKeys(sampletext, cyphertext).take(10)
    probableKeys.foreach { probableKey =>
      val probablePlaintext = cypherFactory(probableKey.key).decypher(cyphertext)
      println(f"${probableKey.distance}%7.2f ${probableKey.key}%-40s : ${probablePlaintext}")
    }

    println()
  }
}