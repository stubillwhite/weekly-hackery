package sudoku

import sudoku.internal.{Puzzle, Solver}

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

  private val exampleSolution =
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

  def main(args: Array[String]): Unit = {
    val puzzle = Puzzle.fromString(examplePuzzle)

    val solution = Solver.solve(puzzle)

    println("--")

    println(solution)
    println(solution.equals(Puzzle.fromString(exampleSolution)))

    println("Done")
  }
}
