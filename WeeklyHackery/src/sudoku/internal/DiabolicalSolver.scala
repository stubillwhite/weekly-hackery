package sudoku.internal

import sudoku.internal.CellAccessor.{applyToCells, columnGroups, regionGroups, rowGroups}
import sudoku.internal.Puzzle._

import scala.annotation.tailrec

object DiabolicalSolver {

  case class Guess(puzzle: Puzzle,
                   coordinate: Coordinate,
                   workingValue: Int)

  case class State(puzzle: Puzzle,
                   guessStack: List[Guess])

  private def removeResolvedNumbersInGroup(cells: Seq[CellValue]): Seq[CellValue] = {
    val resolvedValue: PartialFunction[CellValue, Int] = {
      case Resolved(value) => value
    }

    val resolvedNumbers = cells.collect(resolvedValue).toSet

    cells.map {
      case Unresolved(xs) => Unresolved(xs.diff(resolvedNumbers))
      case Resolved(x) => Resolved(x)
    }
  }

  def findLeastUncertainCell(puzzle: Puzzle): Coordinate = {
    val unresolvedValues: PartialFunction[(Coordinate, CellValue), (Coordinate, Set[Int])] = {
      case (coordinate, Unresolved(values)) => (coordinate, values)
    }

    val cellsByUncertainty = puzzle.grid.collect(unresolvedValues).toList.sortBy { case (_, xs) => xs.size }
    cellsByUncertainty.head._1
  }

  private[internal] def nextState(state: State): Option[State] = {
    val puzzle = repeatUntilUnchanging(removeResolvedNumbers, state.puzzle)

    val coordinate = findLeastUncertainCell(puzzle)
    puzzle.grid(coordinate) match {
      case Unresolved(possibleValues) => {
        if (possibleValues.size == 1) {
          println(s"Deduced ${possibleValues.head} at $coordinate")
          val newPuzzle = puzzle.copy(grid = puzzle.grid.updated(coordinate, Resolved(possibleValues.head)))
          Some(state.copy(puzzle = newPuzzle))
        }
        else {
          if (possibleValues.nonEmpty) {
            val workingValue = possibleValues.toList.sorted.head
            println(s"Making a guess of $workingValue of $possibleValues at $coordinate")

            val guess = Guess(puzzle, coordinate, workingValue)
            val newPuzzle = puzzle.copy(grid = puzzle.grid.updated(coordinate, Resolved(workingValue)))
            Some(state.copy(puzzle = newPuzzle, guessStack = guess :: state.guessStack))
          }
          else {
            val guess = state.guessStack.head
            val possibleValues = guess.puzzle.grid(guess.coordinate).asInstanceOf[Unresolved].possibleValues
            val newPossibleValues = possibleValues - guess.workingValue
            println(s"No possible values at ${coordinate}, backtracking assumption of ${guess.workingValue} at ${guess.coordinate}, old possible values ${possibleValues}, new possible values ${newPossibleValues}")

            val newPuzzle = guess.puzzle.copy(grid = guess.puzzle.grid.updated(guess.coordinate, Unresolved(newPossibleValues)))

            // TODO: What if we're ending up empty here
            Some(state.copy(puzzle = newPuzzle, guessStack = state.guessStack.tail))
          }
        }
      }
    }
  }

  @tailrec
  def repeatUntilUnchanging[A](f: A => A, x: A): A = {
    val next = f(x)
    if (x == next) x
    else repeatUntilUnchanging(f, next)
  }

  def solve(puzzle: Puzzle, displayProgress: Boolean = false): Option[Puzzle] = {

    @tailrec
    def solveIter(state: State): Option[Puzzle] = {
      if (displayProgress) {
        println(s"\033[2J" + s"\033[1;1H" + state.puzzle)
        Thread.sleep(100)
      }

      if (state.puzzle.isSolved) {
        Some(state.puzzle)
      }
      else {
        nextState(state) match {
          case Some(newState) => solveIter(newState)
          case None => None
        }
      }
    }

    solveIter(State(puzzle, List()))
  }

  private def removeResolvedNumbers(puzzle: Puzzle): Puzzle = {
    val allGroups = rowGroups ++ columnGroups ++ regionGroups
    allGroups.foldLeft(puzzle)((acc, x) => applyToCells(acc, x, removeResolvedNumbersInGroup))
  }
}
