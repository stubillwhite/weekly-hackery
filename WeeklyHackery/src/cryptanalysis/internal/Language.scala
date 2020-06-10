package cryptanalysis.internal

trait Language {
  val Letters: Seq[Char]

  val Punctuation: Seq[Char]

  def toLanguage(chars: Seq[Char]): Seq[Char]
}


object EnglishLanguage extends Language {
  override val Letters: Seq[Char] =
    "abcdefghijklmnopqrstuvwxyz"

  override val Punctuation: Seq[Char] =
    " "

  override def toLanguage(chars: Seq[Char]): Seq[Char] = {
    val validChars = Letters.toSet.union(Punctuation.toSet)
    chars
      .map(_.toLower)
      .filter(validChars.contains)
  }
}