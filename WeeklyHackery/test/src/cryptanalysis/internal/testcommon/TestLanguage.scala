package cryptanalysis.internal.testcommon

import cryptanalysis.internal.Language

object TestLanguage extends Language {

  override val Letters: Seq[Char] = "abc"
  override val Punctuation: Seq[Char] = " "

  override def toLanguage(chars: Seq[Char]): Seq[Char] = {
    val validChars = Letters.toSet.union(Punctuation.toSet)
    chars.filter(validChars.contains)
  }
}
