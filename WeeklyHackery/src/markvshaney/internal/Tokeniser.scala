package markvshaney.internal

import markvshaney.internal.MarkovTextGenerator.TextStream

object Tokeniser {

  def tokenise(s: Iterator[String]): TextStream = {
    s.mkString(" ")
      .replaceAll("\n", " ")
      .replaceAll("\".+?\"", " ")
      .split("\\s+")
      .filter(_.matches("\\S+"))
      .toStream
  }
}
