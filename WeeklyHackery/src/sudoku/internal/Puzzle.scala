package sudoku.internal

import sudoku.internal.Puzzle.{Grid, Resolved, Unresolved}

object Puzzle {

  trait CellValue
  case class Resolved(value: Int) extends CellValue
  case class Unresolved(possibleValues: Set[Int]) extends CellValue

  case class Coordinate(x: Int, y: Int)

  type Grid = Map[Coordinate, CellValue]

  val coordinates: Seq[Coordinate] = for {
    y <- 1 to 9
    x <- 1 to 9
  } yield Coordinate(x, y)

  def fromString(s: String): Puzzle = {
    val values = s.split("\n").flatMap(_.split(" "))

    val cellValues = values.map {
      case "." => Unresolved((1 to 9).toSet)
      case x => Resolved(x.toInt)
    }

    Puzzle(coordinates.zip(cellValues).toMap)
  }
}

case class Puzzle(grid: Grid) {

  override def toString: String = {
    val cellValues = Puzzle.coordinates.map(grid(_))
    val values = cellValues.map {
      case Resolved(x) => x
      case Unresolved(_) => "."
    }
    values.grouped(9).map(_.mkString(" ")).mkString("\n")
  }

  def isSolved: Boolean =
    grid.values.forall(_.isInstanceOf[Resolved])
}
