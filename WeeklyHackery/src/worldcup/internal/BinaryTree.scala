package worldcup.internal

sealed trait BinaryTree[A]
case class Branch[A](left: BinaryTree[A], right: BinaryTree[A]) extends BinaryTree[A]
case class Leaf[A](value: A) extends BinaryTree[A]

object BinaryTree {
  def fromSeq[A](nodes: Seq[A]): BinaryTree[A] = {
    nodes match {
      case left :: right :: Nil =>
        Branch(Leaf(left), Leaf(right))
      case _ =>
        val (left, right) = nodes.splitAt(nodes.length/2)
        Branch(fromSeq(left), fromSeq(right))
    }
  }
}