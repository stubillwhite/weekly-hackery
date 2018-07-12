package minesweeper.internal

import minesweeper.internal.Domain.Cells.{Clear, Mine, Warning}

import scala.annotation.tailrec

object Domain {

  sealed trait Cell {
    val isHidden: Boolean
    def reveal(): Cell
  }

  object Cells {
    case class Clear(isHidden: Boolean) extends Cell {
      override def reveal(): Cell = copy(isHidden = false)
    }

    case class Mine(isHidden: Boolean) extends Cell {
      override def reveal(): Cell = copy(isHidden = false)

    }
    case class Warning(isHidden: Boolean, level: Int) extends Cell {
      override def reveal(): Cell = copy(isHidden = false)
    }
  }

  case class Point(x: Int, y: Int) {
    def add(point: Point): Point = {
      Point(x + point.x, y + point.y)
    }
  }

  case class Grid(width: Int,
                  height: Int,
                  cells: Map[Point, Cell],
                  isLost: Boolean) {

    def isWon: Boolean =
      cells.values
        .filter({ _.isHidden })
        .forall(_.isInstanceOf[Mine])

    def neighbours(point: Point): Seq[Point] = {
      val deltas = List(
        Point(-1, -1), Point(0, -1), Point(1, -1),
        Point(-1,  0), /*         */ Point(1,  0),
        Point(-1,  1), Point(0,  1), Point(1,  1)
      )
      deltas.map(point.add).filter(cells.contains)
    }

    def reveal(point: Point): Grid = {
      cells(point) match {
        case mine: Mine =>
          this.copy(cells = cells.updated(point, mine.reveal()), isLost = true)

        case warning: Warning =>
          this.copy(cells = cells.updated(point, warning.reveal()))

        case _ =>
          val newCells = expandFrom(Set(point)).foldLeft(cells)((cs, p) => {
            cs.updated(p, cs(p).reveal())
          })

          this.copy(cells = newCells)
      }
    }

    @tailrec
    private def expandFrom(connectedPoints: Set[Point]): Set[Point] = {
      val newConnectedPoints =
        connectedPoints
          .filter { cells(_).isInstanceOf[Clear] }
          .flatMap(neighbours)

      if (newConnectedPoints == connectedPoints) newConnectedPoints
      else expandFrom(newConnectedPoints)
    }

    override def toString = {
      val cellValues = for {
        y <- 0 until height
        x <- 0 until width
      } yield {
        cells(Point(x, y))
      }

      cellValues
        .sliding(width, width)
        .map(_.map(cellToString))
        .map(_.mkString(""))
        .mkString("\n")
    }
  }

  private def cellToString(cell: Cell): String = {
    val contents = cell match {
      case Warning(_, level) => level.toString
      case Mine(_) => "X"
      case Clear(_) => "."
    }

    if (cell.isHidden) s"[${contents}]"
    else s" ${contents} "
  }
}
