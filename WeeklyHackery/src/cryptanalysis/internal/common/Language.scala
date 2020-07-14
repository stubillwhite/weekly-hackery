package cryptanalysis.internal.common

trait Language {
  val Letters: Seq[Char]

  def toLanguage(chars: Seq[Char]): Seq[Char]
}
