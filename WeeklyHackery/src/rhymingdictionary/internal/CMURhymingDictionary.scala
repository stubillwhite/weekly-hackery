package rhymingdictionary.internal

import rhymingdictionary.internal.CMURhymingDictionary.Phoneme

object CMURhymingDictionary {

  case class Phoneme(sound: String, stress: Option[Int]) {
    override def toString: String = {
      s"${sound}${stress.map(_.toString).getOrElse("")}"
    }
  }

  val vowels = Set(
    "AA",
    "AE",
    "AH",
    "AO",
    "AW",
    "AY",
    "EH",
    "ER",
    "EY",
    "IH",
    "IY",
    "OW",
    "OY",
    "UH",
    "UW",
    "Y"
  )

  val consonants = Set(
    "B",
    "CH",
    "D",
    "DH",
    "F",
    "G",
    "HH",
    "JH",
    "K",
    "L",
    "M",
    "N",
    "NG",
    "P",
    "R",
    "S",
    "SH",
    "T",
    "TH",
    "V",
    "W",
    "Z",
    "ZH"
  )

  def apply(lines: Seq[String]): CMURhymingDictionary =
    new CMURhymingDictionary(lines)
}

class CMURhymingDictionary(lines: Seq[String]) {

  val dictionary: Map[String, Seq[Phoneme]] = {
    val wordLines = lines.filter {
      _.matches("^[a-zA-Z].*$")
    }

    wordLines
      .map(s => (parseWord(s), parsePhonemes(s)))
      .toMap
  }

  def wordsRhymingWith(word: String): Seq[String] = {
    dictionary.keys.filter { rhyme(word, _) }.toList.sorted
  }

  private def rhyme(wordA: String, wordB: String): Boolean = {
    val finalVowelAndConsonants = (phonemes: Seq[Phoneme]) => phonemes.drop(phonemes.lastIndexWhere(ph => CMURhymingDictionary.vowels.contains(ph.sound)))

    finalVowelAndConsonants(dictionary(wordA)) == finalVowelAndConsonants(dictionary(wordB))
  }

  private def parseWord(line: String): String = {
    line.trim.split("\\s+").head
  }

  private def toOptionalInt(stress: String): Option[Int] = {
    if (stress == null) None
    else Some(stress.toInt)
  }

  private def parsePhonemes(line: String): Seq[Phoneme] = {
    val rawPhonemes = line.trim.split("\\s+").tail
    val phonemeRegex = "([A-Z]+)(\\d)?".r

    rawPhonemes.map {
      case phonemeRegex(sound, stress) => Phoneme(sound, toOptionalInt(stress))
    }
  }

}
