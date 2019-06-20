package sudoku.internal

object TestPuzzles {
  val basicPuzzleOne: Puzzle = Puzzle.fromString(
    """. 2 6 . . . 8 1 .
      |3 . . 7 . 8 . . 6
      |4 . . . 5 . . . 7
      |. 5 . 1 . 7 . 9 .
      |. . 3 9 . 5 1 . .
      |. 4 . 3 . 2 . 5 .
      |1 . . . 3 . . . 2
      |5 . . 2 . 4 . . 9
      |. 3 8 . . . 4 6 .
    """.stripMargin)

  val basicPuzzleOneSolution: Puzzle = Puzzle.fromString(
    """7 2 6 4 9 3 8 1 5
      |3 1 5 7 2 8 9 4 6
      |4 8 9 6 5 1 2 3 7
      |8 5 2 1 4 7 6 9 3
      |6 7 3 9 8 5 1 2 4
      |9 4 1 3 6 2 7 5 8
      |1 9 4 8 3 6 5 7 2
      |5 6 7 2 1 4 3 8 9
      |2 3 8 5 7 9 4 6 1
    """.stripMargin)

  val basicPuzzleTwo: Puzzle = Puzzle.fromString(
    """5 3 . . 7 . . . .
      |6 . . 1 9 5 . . .
      |. 9 8 . . . . 6 .
      |8 . . . 6 . . . 3
      |4 . . 8 . 3 . . 1
      |7 . . . 2 . . . 6
      |. 6 . . . . 2 8 .
      |. . . 4 1 9 . . 5
      |. . . . 8 . . 7 9
      |""".stripMargin)

  val basicPuzzleTwoSolution: Puzzle = Puzzle.fromString(
    """5 3 4 6 7 8 9 1 2
      |6 7 2 1 9 5 3 4 8
      |1 9 8 3 4 2 5 6 7
      |8 5 9 7 6 1 4 2 3
      |4 2 6 8 5 3 7 9 1
      |7 1 3 9 2 4 8 5 6
      |9 6 1 5 3 7 2 8 4
      |2 8 7 4 1 9 6 3 5
      |3 4 5 2 8 6 1 7 9
      |""".stripMargin)

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
}
