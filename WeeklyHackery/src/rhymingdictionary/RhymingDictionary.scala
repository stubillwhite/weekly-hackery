package rhymingdictionary

import rhymingdictionary.internal.CMURhymingDictionary

import scala.io.Source

object RhymingDictionary {

  def main(args: Array[String]): Unit = {
    val dictionary = loadDictionary()

    val word = "PURPLE"
    val rhymingWords = dictionary.wordsRhymingWith(word)

    println(s"Words rhyming with ${word} : ${rhymingWords.toList.sorted.mkString(", ")}")
    println("Done")
  }

  private def loadDictionary(): CMURhymingDictionary = {
    val inputStream = getClass.getResourceAsStream("cmudict-0.7b")
    val lines = Source.fromInputStream(inputStream).getLines.toList
    CMURhymingDictionary(lines)
  }
}
