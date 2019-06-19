package worldcup.internal

import worldcup.internal.WorldCup._

object MonteCarlo {

  def monteCarlo(iterations: Int,
                 brackets: BinaryTree[Team],
                 teams: Seq[Team],
                 matchSimulator: MatchSimulator): Map[Team, Int] = {
    Stream.continually(simulateWorldCup(matchSimulator, brackets))
      .take(iterations)
      .foldLeft(Map[Team, Int]()) { case (acc, winner) =>
        acc.updated(winner, acc.getOrElse(winner, 0) + 1)
      }
  }
}
