package markvshaney.internal

import markvshaney.internal.MarkovTextGenerator.TextStream

object TextStreamTrimmer {
  def apply(desiredLength: Int = 100): TextStreamTrimmer = new TextStreamTrimmer(desiredLength)
}

class TextStreamTrimmer(desiredLength: Int = 100) {

  def trim(textStream: TextStream): TextStream = {
    val validStart = textStream.dropWhile(s => !isStart(s))
    takeUntilEnd(desiredLength, validStart)
  }

  private def takeUntilEnd(n: Int, textStream: TextStream): TextStream = {
    if (textStream.isEmpty) {
      textStream
    }
    else {
      if (n < 0 && isEnd(textStream.head)) {
        List(textStream.head).toStream
      }
      else {
        List(textStream.head).toStream.append(takeUntilEnd(n - 1, textStream.tail))
      }
    }
  }

  private def isStart(s: String): Boolean = s(0).toUpper == s(0)

  private def isEnd(s: String): Boolean = s.endsWith(".")
}