package cryptanalysis.internal

object EnglishLanguage extends Language {
  override val Letters: Seq[Char] =
    "abcdefghijklmnopqrstuvwxyz"

  override def toLanguage(chars: Seq[Char]): Seq[Char] = {
    val validChars = Letters.toSet
    chars
      .map(_.toLower)
      .filter(validChars.contains)
  }
}