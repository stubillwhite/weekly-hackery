package labyrinthsareawesome.internal

case class Passage(a: Room, b: Room) {

  override def equals(other: scala.Any): Boolean = {
    other match {
      case passage: Passage =>
        (passage.a == a && passage.b == b) || (passage.a == b && passage.b == a)
      case _ =>
        false
    }
  }
}