package minesweeper.internal

import minesweeper.internal.Domain.Cells.{Clear, Mine, Warning}
import minesweeper.internal.Domain.{Cell, Grid, Point}

import scala.util.Random

object Game {

  def newEasyGame(): Game = {
    Game(generateGrid(10, 10, 5))
  }

  def newMediumGame(): Game = {
    Game(generateGrid(20, 20, 25))
  }

  def newHardGame(): Game = {
    Game(generateGrid(30, 30, 100))
  }

  private def generateGrid(width: Int, height: Int, mineCount: Int): Grid = {
    val points = for {
      y <- 0 until height
      x <- 0 until width
    } yield Point(x, y)

    val clearGrid = Grid(width, height, points.map { point => point -> Clear(true) }.toMap, false)

    val mines = Random.shuffle(points).take(mineCount)
    val mineCells = mines.map { point => point -> Mine(true) }.toMap

    val warningCells = mines
      .flatMap(clearGrid.neighbours)
      .foldLeft(Map[Point, Cell]())(mergeWarnings)

    clearGrid.copy(cells = clearGrid.cells ++ warningCells ++ mineCells)
  }

  private def mergeWarnings(cells: Map[Point, Cell], x: Point): Map[Point, Cell] = {
    cells.get(x) match {
      case (Some(Warning(_, level))) =>
        cells.updated(x, Warning(true, level + 1))

      case _ =>
        cells.updated(x, Warning(true, 1))
    }
  }
}

case class Game(grid: Grid) {

  def reveal(point: Point): Game = {
    this.copy(grid = grid.reveal(point))
  }

  def isWon: Boolean =
    grid.isWon

  def isLost: Boolean =
    grid.isLost
}