package bowlingkata

import scala.annotation.tailrec

object BowlingKata {

  type Roll = Int

  type Game = List[Roll]

  def scoreGame(rolls: List[Roll]): Int = {

    @tailrec
    def scoreIter(frame: Int, score: Int, rolls: List[Roll]): Int = {
      if (frame == 11) {
        score
      }
      else {
        val nextFrame = frame + 1
        rolls match {
          case Nil => score
          case a :: Nil => score + a

          case 10 :: rest => scoreIter(nextFrame, score + 10 + rest.take(2).sum, rest)
          case a :: b :: rest if a + b == 10 => scoreIter(nextFrame, score + 10 + rest.take(1).sum, rest)
          case a :: b :: rest => scoreIter(nextFrame, score + a + b, rest)
        }
      }
    }

    scoreIter(1, 0, rolls)
  }
}
