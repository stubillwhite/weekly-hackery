package cryptanalysis.internal

import java.lang.Math.{floor, sqrt}

object VigenereCypherBreaker {
  def apply(language: Language): VigenereCypherBreaker = new VigenereCypherBreaker(language)

  private[cryptanalysis] def vigenereKeySeq(probableKeys: Seq[Seq[ProbableKey[CaesarCypherKey]]]): Seq[ProbableKey[VigenereCypherKey]] = {

    def makeKey(ks: Seq[Seq[ProbableKey[CaesarCypherKey]]]): ProbableKey[VigenereCypherKey] = {
      val offsets = ks.map(_.head.key.offset)
      val totalDistance = ks.map(_.head.distance).sum
      ProbableKey(VigenereCypherKey(offsets), totalDistance)
    }

    def nextMostProbableCombination(keys: Seq[Seq[ProbableKey[CaesarCypherKey]]]): Seq[Seq[ProbableKey[CaesarCypherKey]]] = {
      val remainingPossibilities = keys
        .zipWithIndex
        .filter { case (ks, _) => ks.length > 1 }

      if (remainingPossibilities.isEmpty) {
        Nil
      }
      else {
        val (k, index) = remainingPossibilities.minBy(_._1(1).distance)
        keys.updated(index, keys(index).tail)
      }
    }

    Iterator
      .iterate(probableKeys)(nextMostProbableCombination)
      .takeWhile(_ != Nil)
      .map(makeKey)
      .toSeq
  }

}

class VigenereCypherBreaker(language: Language) extends CypherBreaker[VigenereCypher, VigenereCypherKey] {

  override def probableKeys(sampletext: String, cyphertext: String): Seq[ProbableKey[VigenereCypherKey]] = {
    val keyLengthsToTake = 4
    val keyLengths = probableKeyLengths(cyphertext).take(keyLengthsToTake)

    println(s"Probable key lengths:")
    for {
      probableKeyLength <- keyLengths
    } yield {
      println(s"  ${probableKeyLength.length} (${probableKeyLength.coincidences} coincidences)")
    }

    val keys = for {
      probableKeyLength <- keyLengths
      cyphertexts = createTextsForPeriod(cyphertext, probableKeyLength.length)
      possibleKeys = cyphertexts.map(cyphertext => CaesarCypherBreaker(language).probableKeys(sampletext, cyphertext))
    } yield {
      VigenereCypherBreaker.vigenereKeySeq(possibleKeys)
    }

    val sampletextDistribution = FrequencyDistribution(language, sampletext)
    keys
      .flatten.map { k =>
      val distribution = FrequencyDistribution(language, VigenereCypher(language, k.key).decypher(cyphertext))
      (k, distribution.distance(sampletextDistribution))
    }
      .sortBy(_._2)
      .map { case (k, d) =>
        ProbableKey(k.key, d)
      }
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

  private[internal] def createTextsForPeriod(text: String, step: Int): Seq[String] = {
    for {
      n <- 0 until step
    } yield {
      text.grouped(step).toList.map(s => s.lift(n).getOrElse("")).mkString
    }
  }
}
