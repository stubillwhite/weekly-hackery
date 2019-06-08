package sudoku.internal

import sudoku.internal.Puzzle.{Cell, Resolved, Unresolved}
import sudoku.internal.CellAccessor.{applyToCells, columnGroups, regionGroups, rowGroups}

import scala.annotation.tailrec

object Solver {

  private def removeResolvedNumbersInGroup(cells: Seq[Cell]): Seq[Cell] = {
    val resolvedNumbers = cells
      .filter(_.isInstanceOf[Resolved])
      .map(_.asInstanceOf[Resolved].value)
      .toSet

    cells.map {
      case Unresolved(xs) => {
        val possibleValues = xs.diff(resolvedNumbers)

        if (possibleValues.size == 1) Resolved(possibleValues.head)
        else Unresolved(possibleValues)
      }
      case Resolved(x) => Resolved(x)
    }
  }

  def resolve(puzzle: Puzzle): Puzzle = {
    val allGroups = rowGroups ++ columnGroups ++ regionGroups
    allGroups.foldLeft(puzzle)((acc, x) => applyToCells(acc, x, removeResolvedNumbersInGroup))
  }

  @tailrec
  def solve(puzzle: Puzzle): Puzzle = {
    if (puzzle.grid.values.forall(_.isInstanceOf[Resolved])) puzzle
    else {
      println("--")
      println(puzzle)
      solve(resolve(puzzle))
    }
  }
}
