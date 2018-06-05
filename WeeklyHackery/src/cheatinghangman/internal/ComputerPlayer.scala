package cheatinghangman.internal

import scala.annotation.tailrec

object ComputerPlayer {
  def apply(wordList: List[String]): ComputerPlayer =
    new ComputerPlayer(wordList)
}

class ComputerPlayer(wordList: List[String]) extends Player {

  private val AllLetters = "abcdefghijklmnopqrstuvwxyz".toSet

  override def guessCharacter(word: List[Option[Char]],
                              guessedLetters: Set[Char],
                              remainingLives: Int): Char = {

    val possibleGuesses = AllLetters.diff(guessedLetters)

    // Filter to words with the correct length and known letters, then count the words per letter

    val possibleWordsPerLetter = wordList
      .filter(x => x.length == word.length)
      .filter(x => matchesKnownLetters(x, word))
      .flatMap(x => x.toSet.intersect(possibleGuesses))
      .foldLeft(Map[Char, Int]())((acc, ch) => {
        acc.updated(ch, acc.getOrElse(ch, 0) + 1)
      })

    // Best guess is the letter which occurs in the most words

    val (ch, n) = possibleWordsPerLetter.maxBy(_._2)

    println
    println(s"Computer player: My best guess is '${ch}', which is in ${n} word(s)")
    println

    ch
  }

  @tailrec
  private def matchesKnownLetters(s: String, word: List[Option[Char]]): Boolean = {
    if (s.isEmpty) {
      true
    }
    else {
      (s.head, word.head) match {
        case (a, Some(b)) if a != b => false
        case _ => matchesKnownLetters(s.tail, word.tail)
      }
    }
  }
}
