package cryptanalysis.internal

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

  private[internal] def createTextsForPeriod(text: String, step: Int): Seq[String] = {
    for {
      n <- 0 until step
    } yield {
      text
        .grouped(step)
        .toList
        .map(s => s.lift(n).getOrElse(""))
        .mkString
    }
  }

  override def probableKeys(sampletext: String, cyphertext: String): Seq[ProbableKey[VigenereCypherKey]] = {
    val periodIdentifier = KeyPeriodIdentifier(language, cyphertext.length / 10)
    val probableKeyLength = periodIdentifier.probableKeyPeriod(cyphertext)
    println(s"Probable length: ${probableKeyLength}")

    val cyphertexts: Seq[String] = createTextsForPeriod(cyphertext, probableKeyLength)
    val possibleKeys: Seq[Seq[ProbableKey[CaesarCypherKey]]] = cyphertexts.map(cyphertext => CaesarCypherBreaker(language).probableKeys(sampletext, cyphertext))

    VigenereCypherBreaker.vigenereKeySeq(possibleKeys)
  }
}
