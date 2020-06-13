package cryptanalysis.internal

trait Language {
  val Letters: Seq[Char]

  val Punctuation: Seq[Char]

  def toLanguage(chars: Seq[Char]): Seq[Char]
}


