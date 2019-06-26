package worldcup.internal

import scala.util.Random

case class Team(name: String, points: Int)

object WorldCup {

  private val random: Random = new Random(System.currentTimeMillis())

  private def toTeams(rankings: Seq[(String, Int)]): Seq[Team] = {
    rankings.map { case (name, points) => Team(name, points) }
  }

  val WOMENS_WORLD_CUP_TEAMS_2019: Seq[Team] = toTeams(List(
    ("Norway", 1915),
    ("Australia", 2003),
    ("England", 2049),
    ("Cameroon", 1499),
    ("France", 2043),
    ("Brazil", 1944),
    ("Spain", 1913),
    ("USA", 2101),
    ("Italy", 1868),
    ("China", 1866),
    ("Netherlands", 1967),
    ("Japan", 1991),
    ("Germany", 2072),
    ("Nigeria", 1599),
    ("Sweden", 1962),
    ("Canada", 2006)
  ))

  type MatchSimulator = (Team, Team) => Team

  def strongestTeamAlwaysWins(a: Team, b: Team): Team = {
    val winner = if (a.points > b.points) a else b
    println(f"${a.name} vs ${b.name} : ${winner.name} won")
    winner
  }

  private def winProbability(a: Team, b: Team): Double = {
    // P(A) = 1/(1+10^m) where m is the rating difference (rating(B)-rating(A)) divided by 400
    val m = (b.points - a.points) / 400.0
    100.0 * 1.0 / (1.0 + Math.pow(10.0, m))
  }

  def strongestTeamProbablyWins(a: Team, b: Team): Team = {
    val probA = winProbability(a, b)
    val probB = winProbability(b, a)

    val winner = if (random.nextDouble() * 100.0 <= probA) a else b
    println(f"${a.name} ($probA%.1f%%) vs ${b.name} ($probB%.1f%%) : ${winner.name} won")
    winner
  }

  def simulateWorldCup(matchSimulator: MatchSimulator, bracket: BinaryTree[Team]): Team = {

    def iter(bracket: BinaryTree[Team]): Team = {
      bracket match {
        case Branch(left, right) =>
          matchSimulator(iter(left), iter(right))

        case Leaf(a) => a
      }
    }

    val winner = iter(bracket)
    println(s"Winner: ${winner.name}\n")
    winner
  }
}