package sudoku

import sudoku.internal.{Puzzle, DiabolicalSolver}

object Sudoku {

  val diabolicalPuzzleOne: Puzzle = Puzzle.fromString(
    """. . . 9 2 . . . .
      |. . 6 8 . 3 . . .
      |1 9 . . 7 . . . 6
      |2 3 . . 4 . 1 . .
      |. . 1 . . . 7 . .
      |. . 8 . 3 . . 2 9
      |7 . . . 8 . . 9 1
      |. . . 5 . 7 2 . .
      |. . . . 6 4 . . .
      |""".stripMargin)

  val diabolicalPuzzleOneSolution: Puzzle = Puzzle.fromString(
    """3 8 7 9 2 6 4 1 5
      |5 4 6 8 1 3 9 7 2
      |1 9 2 4 7 5 8 3 6
      |2 3 5 7 4 9 1 6 8
      |9 6 1 2 5 8 7 4 3
      |4 7 8 6 3 1 5 2 9
      |7 5 4 3 8 2 6 9 1
      |6 1 3 5 9 7 2 8 4
      |8 2 9 1 6 4 3 5 7
      |""".stripMargin
  )

  def main(args: Array[String]): Unit = {
    DiabolicalSolver.solve(diabolicalPuzzleOne, displayProgress = true)

    println("Done")
  }
}
