package cryptanalysis.internal

//object VigenereCypherBreaker {
//  def apply(languageModel: LanguageModel): VigenereCypherBreaker = new VigenereCypherBreaker(languageModel)
//}
//
//class VigenereCypherBreaker(languageModel: LanguageModel) extends CypherBreaker[VigenereCypher, VigenereCypherKey] {
//
//  override def probableKeys(cyphertext: String): Seq[ProbableKey[VigenereCypherKey]] = {
//    val keys = for {
//      keyLength <- 1 to cyphertext.length
//      cyphertexts = createSampledTexts(cyphertext, keyLength)
//      keys = cyphertexts.map(CaesarCypherBreaker(languageModel).probableKeys(_).head)
//    } yield {
//      ProbableKey(VigenereCypherKey(keys.map(_.key.offset).toList), keys.map(_.distance).sum)
//    }
//
//    keys.sortBy(_.distance)
//  }
//
//  private[internal] def createSampledTexts(text: String, step: Int): Seq[String] = {
//    for {
//      n <- 0 until step
//    } yield {
//      text.grouped(step).toList.map(s => s.lift(n).getOrElse("")).mkString
//    }
//  }
//}
