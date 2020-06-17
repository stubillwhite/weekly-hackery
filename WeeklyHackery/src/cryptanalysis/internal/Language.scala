package cryptanalysis.internal

trait Language {
  val Letters: Seq[Char]

  def toLanguage(chars: Seq[Char]): Seq[Char]
}


