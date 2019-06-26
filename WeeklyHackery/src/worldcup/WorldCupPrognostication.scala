package worldcup

import worldcup.internal.BinaryTree
import worldcup.internal.MonteCarlo.monteCarlo
import worldcup.internal.WorldCup.{WOMENS_WORLD_CUP_TEAMS_2019, strongestTeamProbablyWins}

object WorldCupPrognostication {

  def main(args: Array[String]): Unit = {
    val teams = WOMENS_WORLD_CUP_TEAMS_2019

    val brackets = BinaryTree.fromSeq(teams)

    val n = 10
    val results = monteCarlo(n, brackets, teams, strongestTeamProbablyWins)

    println("--")

    results
      .toList
      .sortBy { case (_, wins) => wins }
      .reverse
      .foreach { case (team, wins) => println(f"${100.0 * wins.toDouble / n}%.01f%% \t ${team.name}") }
  }
}
