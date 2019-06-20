package sudoku.internal

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import sudoku.internal.DiabolicalSolver.State
import sudoku.internal.Puzzle.{Coordinate, Unresolved}
import sudoku.internal.TestPuzzles._

class DiabolicalSolverTest extends FlatSpec with Matchers with MockitoSugar {

  behavior of "solve"

  it should "solve basic puzzle one" in {
    DiabolicalSolver.solve(basicPuzzleOne) should be(Some(basicPuzzleOneSolution))
  }

  it should "solve basic puzzle two" in {
    DiabolicalSolver.solve(basicPuzzleTwo) should be(Some(basicPuzzleTwoSolution))
  }

  it should "solve diabolical puzzle one" in {
    DiabolicalSolver.solve(diabolicalPuzzleOne) should be(Some(diabolicalPuzzleOneSolution))
  }

  it should "terminate without finding a solution if a puzzle in unsolvable" in {
    val unsolvablePuzzle = Puzzle.fromString(
      """1 2 3 4 5 6 7 8 .
        |1 2 3 4 5 6 7 8 9
        |1 2 3 4 5 6 7 8 9
        |1 2 3 4 5 6 7 8 9
        |1 2 3 4 5 6 7 8 9
        |1 2 3 4 5 6 7 8 9
        |1 2 3 4 5 6 7 8 9
        |1 2 3 4 5 6 7 8 9
        |1 2 3 4 5 6 7 8 9
      """.stripMargin)
    DiabolicalSolver.solve(unsolvablePuzzle)
  }

  behavior of "nextState"

  it should "solve the the puzzle if " in {
    val grid = basicPuzzleOneSolution.grid.updated(Coordinate(1, 1), Unresolved(Set(7)))
    val state = State(Puzzle(grid), List())
    DiabolicalSolver.nextState(state) should be(basicPuzzleOneSolution)
  }

  it should "return the same state if a puzzle is solved" in {
    val state = State(basicPuzzleOneSolution, List())
    DiabolicalSolver.nextState(state) should be(state)
  }
}
