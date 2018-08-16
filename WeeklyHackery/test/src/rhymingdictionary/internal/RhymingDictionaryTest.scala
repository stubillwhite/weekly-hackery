package rhymingdictionary.internal

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class RhymingDictionaryTest extends FlatSpec with Matchers with MockitoSugar {

  /*
 * | Phoneme | Example | Translation |
 * |---------+---------+-------------|
 * | AA      | odd     | AA D        |
 * | AE      | at      | AE T        |
 * | AH      | hut     | HH AH T     |
 * | AO      | ought   | AO T        |
 * | AW      | cow     | K AW        |
 * | AY      | hide    | HH AY D     |
 * | B       | be      | B IY        |
 * | CH      | cheese  | CH IY Z     |
 * | D       | dee     | D IY        |
 * | DH      | thee    | DH IY       |
 * | EH      | Ed      | EH D        |
 * | ER      | hurt    | HH ER T     |
 * | EY      | ate     | EY T        |
 * | F       | fee     | F IY        |
 * | G       | green   | G R IY N    |
 * | HH      | he      | HH IY       |
 * | IH      | it      | IH T        |
 * | IY      | eat     | IY T        |
 * | JH      | gee     | JH IY       |
 * | K       | key     | K IY        |
 * | L       | lee     | L IY        |
 * | M       | me      | M IY        |
 * | N       | knee    | N IY        |
 * | NG      | ping    | P IH NG     |
 * | OW      | oat     | OW T        |
 * | OY      | toy     | T OY        |
 * | P       | pee     | P IY        |
 * | R       | read    | R IY D      |
 * | S       | sea     | S IY        |
 * | SH      | she     | SH IY       |
 * | T       | tea     | T IY        |
 * | TH      | theta   | TH EY T AH  |
 * | UH      | hood    | HH UH D     |
 * | UW      | two     | T UW        |
 * | V       | vee     | V IY        |
 * | W       | we      | W IY        |
 * | Y       | yield   | Y IY L D    |
 * | Z       | zee     | Z IY        |
 * | ZH      | seizure | S IY ZH ER  |
 */

  behavior of "wordsRhymingWith"

  it should "return words which match on the final vowel and consonants" in {
    // Given
    val dictionary = dictionaryWithWords(
      "FOOT F UH2 T",
      "GOOT G UH2 T"
    )

    // When, Then
    dictionary.wordsRhymingWith("FOOT") should be(List("FOOT", "GOOT"))
  }

  it should "ignore words which differ on the final vowel sound" in {
    // Given
    val dictionary = dictionaryWithWords(
      "FOOT F UH2 T",
      "GOYT G OY2 T"
    )

    // When, Then
    dictionary.wordsRhymingWith("FOOT") should be(List("FOOT"))
  }

  it should "ignore words which differ on the final vowel stress" in {
    // Given
    val dictionary = dictionaryWithWords(
      "FOOT F UH2 T",
      "GOOT G UH1 T"
    )

    // When, Then
    dictionary.wordsRhymingWith("FOOT") should be(List("FOOT"))
  }

  it should "ignore words which differ on the final vowel stress by being unstressed" in {
    // Given
    val dictionary = dictionaryWithWords(
      "FOOT F UH2 T",
      "GOOT G UH T"
    )

    // When, Then
    dictionary.wordsRhymingWith("FOOT") should be(List("FOOT"))
  }

  private def dictionaryWithWords(words: String*): CMURhymingDictionary = {
    CMURhymingDictionary(words)
  }
}
