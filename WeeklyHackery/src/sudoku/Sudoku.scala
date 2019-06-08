package sudoku

import scala.collection.immutable

object Sudoku {

  private val examplePuzzle =
    """. 2 6 . . . 8 1 .
      |3 . . 7 . 8 . . 6
      |4 . . . 5 . . . 7
      |. 5 . 1 . 7 . 9 .
      |. . 3 9 . 5 1 . .
      |. 4 . 3 . 2 . 5 .
      |1 . . . 3 . . . 2
      |5 . . 2 . 4 . . 9
      |. 3 8 . . . 4 6 .
    """.stripMargin

  private val solution =
    """7 2 6 4 9 3 8 1 5
      |3 1 5 7 2 8 9 4 6
      |4 8 9 6 5 1 2 3 7
      |8 5 2 1 4 7 6 9 3
      |6 7 3 9 8 5 1 2 4
      |9 4 1 3 6 2 7 5 8
      |1 9 4 8 3 6 5 7 2
      |5 6 7 2 1 4 3 8 9
      |2 3 8 5 7 9 4 6 1
    """.stripMargin

  trait Cell

  case class Resolved(value: Int) extends Cell

  case class Unresolved(possibleValues: Set[Int]) extends Cell

  case class Coordinate(x: Int, y: Int)

  type Puzzle = Map[Coordinate, Cell]

  def puzzleToString(p: Puzzle): String = {
    val cells = coordinates.map(p(_))
    val values = cells.map {
      case Resolved(x) => x
      case Unresolved(_) => "."
    }
    values.grouped(9).map(_.mkString(" ")).mkString("\n")
  }

  private val coordinates: Seq[Coordinate] = for {
    y <- 1 to 9
    x <- 1 to 9
  } yield Coordinate(x, y)

  private val rowSlices: Seq[Seq[Coordinate]] =
    coordinates.grouped(9).toSeq

  private val columnSlices: Seq[Seq[Coordinate]] =
    (for {
      x <- 1 to 9
      y <- 1 to 9
    } yield Coordinate(x, y)).grouped(9).toSeq

  private val regionSlices: Seq[Seq[Coordinate]] = {
    val withinRegionCoordinates: Seq[Coordinate] = for {
      y <- 1 to 3
      x <- 1 to 3
    } yield Coordinate(x, y)

    for {
      offsetX <- 0 to 2
      offsetY <- 0 to 2
    } yield withinRegionCoordinates.map {
      case Coordinate(x, y) => Coordinate(offsetX * 3 + x, offsetY * 3 + y)
    }
  }

  def toPuzzle(s: String): Puzzle = {
    val values = s.split("\n").flatMap(_.split(" "))

    val cells = values.map {
      case "." => Unresolved((1 to 9).toSet)
      case x => Resolved(x.toInt)
    }

    coordinates.zip(cells).toMap
  }

  // TODO: Rename
  def applyToCells(f: Seq[Cell] => Seq[Cell],
                   coordinates: Seq[Coordinate],
                   puzzle: Puzzle): Puzzle = {
    val values = f(coordinates.map(puzzle(_)))
    coordinates.zip(values).foldLeft(puzzle)((acc, x) => acc + x)
  }

  def removeResolved(cells: Seq[Cell]): Seq[Cell] = {
    val resolved = cells
      .filter(_.isInstanceOf[Resolved])
      .map(_.asInstanceOf[Resolved].value)
      .toSet

    cells.map {
      case Unresolved(xs) => {
        val possibleValues = xs.diff(resolved)
        if (possibleValues.size == 1) Resolved(possibleValues.head)
        else Unresolved(possibleValues)
      }
      case Resolved(x) => Resolved(x)
    }
  }

  def resolve(puzzle: Puzzle): Puzzle = {
    val allSlices = rowSlices ++ columnSlices ++ regionSlices
    allSlices.foldLeft(puzzle)((acc, x) => applyToCells(removeResolved, x, acc))
  }

  def main(args: Array[String]): Unit = {
    val puzzle = toPuzzle(examplePuzzle)
    println(puzzle)
    println(puzzleToString(puzzle))

    

    println("--")

    val newPuzzle = resolve(puzzle)
    println(puzzleToString(newPuzzle))

    println("--")

    println(puzzleToString(resolve(newPuzzle)))

    println("Done")
  }
}
