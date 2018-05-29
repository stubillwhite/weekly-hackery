package markvshaney

import markvshaney.internal.MarkovTextGenerator.TextStream
import markvshaney.internal.{MarkovTextGenerator, TextStreamTrimmer, Tokeniser}

import scala.io.Source

object MarkVShaney {

  def main(args: Array[String]): Unit = {
    val generator = MarkovTextGenerator(sourceText())
    val textStreamTrimmer = TextStreamTrimmer(100)

    val textStream = generator.generateText()
    val filteredTextStream = textStreamTrimmer.trim(textStream)

    println(filteredTextStream.mkString(" "))
  }

  private def sourceText(): TextStream = {
    val lines =
      readLines("/markvshaney/my-man-jeeves.txt") ++
      readLines("/markvshaney/right-ho-jeeves.txt") ++
        readLines("/markvshaney/the-dunwich-horror.txt")

    Tokeniser.tokenise(lines)
  }

  private def readLines(resourceName: String): Iterator[String] = {
    val inputStream = getClass.getResourceAsStream(resourceName)
    Source.fromInputStream(inputStream).getLines
  }
}
