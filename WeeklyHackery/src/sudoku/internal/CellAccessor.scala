package sudoku.internal

import sudoku.internal.Puzzle.{CellValue, Coordinate}

object CellAccessor {

  val rowGroups: Seq[Seq[Coordinate]] =
    (for {
      y <- 1 to 9
      x <- 1 to 9
    } yield Coordinate(x, y)).grouped(9).toSeq

  val columnGroups: Seq[Seq[Coordinate]] =
    (for {
      x <- 1 to 9
      y <- 1 to 9
    } yield Coordinate(x, y)).grouped(9).toSeq

  val regionGroups: Seq[Seq[Coordinate]] = {
    val withinGroupCoordinates: Seq[Coordinate] = for {
      y <- 1 to 3
      x <- 1 to 3
    } yield Coordinate(x, y)

    for {
      offsetX <- 0 to 2
      offsetY <- 0 to 2
    } yield withinGroupCoordinates.map {
      case Coordinate(x, y) => Coordinate(offsetX * 3 + x, offsetY * 3 + y)
    }
  }

  def applyToCells(puzzle: Puzzle,
                   coordinates: Seq[Coordinate],
                   f: Seq[CellValue] => Seq[CellValue]): Puzzle = {
    val newValues = f(coordinates.map(puzzle.grid(_)))
    val newGrid = coordinates.zip(newValues).foldLeft(puzzle.grid)((acc, x) => acc + x)
    puzzle.copy(grid = newGrid)
  }
}
