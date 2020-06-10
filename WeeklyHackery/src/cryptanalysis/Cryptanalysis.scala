import cryptanalysis.internal.{CaesarCypher, CaesarCypherBreaker, CaesarCypherKey, EnglishLanguage, FrequencyDistribution}
import markvshaney.MarkVShaney.getClass

import scala.io.Source
import scala.util.Random

object Cryptanalysis {

  def main(args: Array[String]): Unit = {
    testCaesarCypher
    //    testVigenereCypher
  }

  private def testVigenereCypher(): Unit = {
    //    val key = VigenereCypherKey(List(1, 2, 3, 4))
    //    val cypher = VigenereCypher
    //
    //    val plaintext = filter("""This is some sample text that we're going to encrypt. Longer text will be more likely to be decoded correctly. Let's add a few more words to see if that works out a little better.""")
    //
    //    val cyphertext = cypher().encypher(EnglishLanguage, key, plaintext)
    //    println(cyphertext)
    //    println(cypher().decypher(EnglishLanguage, key, cyphertext))
    //
    //    val sampleText = filter("""This is a sample piece of the original language. It won't be the same as the plaintext but it should be similar enough. Let's add a few more words to see if that works out a little better.""")
    //    val model = LanguageModel(sampleText.filter(_ != ' '))
    //
    //    val breaker = VigenereCypherBreaker(model)
    //    for {
    //      probableKey <- breaker.probableKeys(cyphertext).take(3)
    //      probablePlaintext = VigenereCypher().decypher(probableKey.key, cyphertext)
    //    } yield {
    //      println(s"${probableKey} : ${probablePlaintext}")
    //    }
  }

  private def testCaesarCypher(): Unit = {
    val key = CaesarCypherKey(19)
    val cypher = CaesarCypher(EnglishLanguage, key)
    val plaintext = """a quick brown dog jumps over the lazy fox"""

    val cyphertext = cypher.encypher(plaintext)
    println(cyphertext)
    println(cypher.decypher(cyphertext))

    val sampletext = """This is a sample piece of the original language. It won't be the same as the plaintext but it should be similar enough."""
//    val sampletext = Source.fromInputStream(getClass.getResourceAsStream("markvshaney/right-ho-jeeves.txt")).getLines.mkString
    println(FrequencyDistribution(EnglishLanguage, sampletext).RelativeFrequencies)
    println(FrequencyDistribution(EnglishLanguage, cyphertext).RelativeFrequencies)

    println(FrequencyDistribution(EnglishLanguage, sampletext).RelativeFrequencies)
    println(FrequencyDistribution(EnglishLanguage, sampletext).rotate(0).RelativeFrequencies)
    println(FrequencyDistribution(EnglishLanguage, cyphertext).rotate(7).RelativeFrequencies)

    val breaker = CaesarCypherBreaker(EnglishLanguage)
    for {
      probableKey <- breaker.probableKeys(sampletext, cyphertext)
      probablePlaintext = CaesarCypher(EnglishLanguage, probableKey.key).decypher(cyphertext)
    } yield {
      println(s"${probableKey} : ${probablePlaintext}")
    }
  }
}