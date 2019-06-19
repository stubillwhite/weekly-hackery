package worldcup.internal

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class BinaryTreeTest extends FlatSpec with Matchers with MockitoSugar {

  private def toTeams(rankings: Seq[(String, Int)]): Seq[Team] = {
    rankings.map { case (name, points) => Team(name, points) }
  }

  val STUB_TEAMS: Seq[String] = "ABCDEFGHIJKLMNOP".toList.map(_.toString)

  behavior of "fromSeq"

  it should "build a binary tree from bottom up" in {
    val actual = BinaryTree.fromSeq(STUB_TEAMS)

    actual should be
    Branch(
      Branch(
        Branch(
          Branch(Leaf("A"), Leaf("B")),
          Branch(Leaf("C"), Leaf("D"))),
        Branch(
          Branch(Leaf("E"), Leaf("F")),
          Branch(Leaf("G"), Leaf("H")))),
      Branch(
        Branch(
          Branch(Leaf("I"), Leaf("J")),
          Branch(Leaf("K"), Leaf("L"))),
        Branch(
          Branch(Leaf("M"), Leaf("N")),
          Branch(Leaf("O"), Leaf("P")))))
  }
}
