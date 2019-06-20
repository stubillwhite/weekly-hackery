package sudoku.internal

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import sudoku.internal.TestPuzzles._

class BasicSolverTest extends FlatSpec with Matchers with MockitoSugar {

  behavior of "solve"

  it should "solve basic puzzle one" in {
    BasicSolver.solve(basicPuzzleOne) should be(basicPuzzleOneSolution)
  }

  it should "solve basic puzzle two" in {
    BasicSolver.solve(basicPuzzleTwo) should be(basicPuzzleTwoSolution)
  }
}
