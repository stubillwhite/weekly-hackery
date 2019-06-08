package sudoku.internal

import sudoku.internal.Puzzle.{Grid, Resolved, Unresolved}

object Puzzle {

  trait Cell

  case class Resolved(value: Int) extends Cell
  case class Unresolved(possibleValues: Set[Int]) extends Cell
  case class Coordinate(x: Int, y: Int)

  type Grid = Map[Coordinate, Cell]

  val coordinates: Seq[Coordinate] = for {
    y <- 1 to 9
    x <- 1 to 9
  } yield Coordinate(x, y)

  def fromString(s: String): Puzzle = {
    val values = s.split("\n").flatMap(_.split(" "))

    val cells = values.map {
      case "." => Unresolved((1 to 9).toSet)
      case x => Resolved(x.toInt)
    }

    Puzzle(coordinates.zip(cells).toMap)
  }
}

case class Puzzle(grid: Grid) {

  override def toString: String = {
    val cells = Puzzle.coordinates.map(grid(_))
    val values = cells.map {
      case Resolved(x) => x
      case Unresolved(_) => "."
    }
    values.grouped(9).map(_.mkString(" ")).mkString("\n")
  }
}
