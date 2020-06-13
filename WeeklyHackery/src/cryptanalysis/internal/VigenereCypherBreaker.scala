package cryptanalysis.internal

import java.lang.Math.{floor, sqrt}

object VigenereCypherBreaker {
  def apply(language: Language): VigenereCypherBreaker = new VigenereCypherBreaker(language)
}

class VigenereCypherBreaker(language: Language) extends CypherBreaker[VigenereCypher, VigenereCypherKey] {

  override def probableKeys(sampletext: String, cyphertext: String): Seq[ProbableKey[VigenereCypherKey]] = {
    val keyLengthsToTake = 3
    println(s"Probable key lengths: ${probableKeyLengths(cyphertext).take(keyLengthsToTake)}")

    val keys = for {
      probableKeyLength <- probableKeyLengths(cyphertext).take(keyLengthsToTake)
      cyphertexts = createSampledTexts(cyphertext, probableKeyLength.length)
      _ = println(cyphertexts.mkString("\n"), "--")
      possibleKey = cyphertexts.map(cyphertext => CaesarCypherBreaker(language).probableKeys(sampletext, cyphertext).head)
    } yield {
      ProbableKey(VigenereCypherKey(possibleKey.map(_.key.offset).toList), possibleKey.map(_.distance).sum)
    }

    keys.sortBy(_.distance)
  }

  private def factors(n: Int): Set[Int] = {
    val limit = floor(sqrt(n.toDouble)).toInt + 1

    Stream.range(2, limit)
      .filter(n % _ == 0)
      .flatMap(x => Array(x, n / x))
      .toSet
  }

  case class ProbableKeyLength(length: Int, coincidences: Int)

  private def probableKeyLengths(cyphertext: String): List[ProbableKeyLength] = {

    def coincidentCharacters(a: Seq[Char], b: Seq[Char]): Int = {
      a.zip(b).count { case (a, b) => a == b }
    }

    val keyLengthAndCounts = for {
      keyLength <- 1 to cyphertext.length
    } yield {
      keyLength -> coincidentCharacters(cyphertext, cyphertext.drop(keyLength))
    }

    keyLengthAndCounts
      .filter { case (keyLength, count) => count > 1 }
      .flatMap { case (keyLength, count) => factors(keyLength) ++ Set(keyLength) }
      .groupBy(identity)
      .mapValues(_.length)
      .map { case (keyLength, coincidences) => ProbableKeyLength(keyLength, coincidences) }
      .toList
      .sortBy(_.coincidences)
      .reverse
  }

  private[internal] def createSampledTexts(text: String, step: Int): Seq[String] = {
    for {
      n <- 0 until step
    } yield {
      text.grouped(step).toList.map(s => s.lift(n).getOrElse("")).mkString
    }
  }
}
