package sudoku.internal

import sudoku.internal.Puzzle.{CellValue, Resolved, Unresolved}
import sudoku.internal.CellAccessor.{applyToCells, columnGroups, regionGroups, rowGroups}

import scala.annotation.tailrec

object BasicSolver {

  private def resolveNumbersInGroup(cellValues: Seq[CellValue]): Seq[CellValue] = {
    val resolvedValue: PartialFunction[CellValue, Int] = {
      case Resolved(value) => value
    }

    val resolvedNumbers = cellValues.collect(resolvedValue).toSet

    cellValues.map {
      case Resolved(x) => Resolved(x)

      case Unresolved(xs) => {
        val possibleValues = xs.diff(resolvedNumbers)

        if (possibleValues.size == 1) Resolved(possibleValues.head)
        else Unresolved(possibleValues)
      }
    }
  }

  def resolveKnownNumbers(puzzle: Puzzle): Puzzle = {
    val allGroups = rowGroups ++ columnGroups ++ regionGroups
    allGroups.foldLeft(puzzle)((acc, x) => applyToCells(acc, x, resolveNumbersInGroup))
  }

  @tailrec
  def solve(puzzle: Puzzle): Puzzle = {
    if (puzzle.isSolved) puzzle
    else solve(resolveKnownNumbers(puzzle))
  }
}
