package gameofblobs

import gameofblobs.GameOfBlobs.randomBlob
import gameofblobs.internal.domain.{Blob, Environment, Location, updateState}

import scala.util.Random

object GameOfBlobs {

  implicit class TakeUntilIteratorWrapper[T](list: Iterator[T]) {
    def takeUntil(predicate: T => Boolean): Iterator[T] = {
      list.span(!predicate(_)) match {
        case (head, tail) => head ++ tail.take(1)
      }
    }
  }

  def environmentToString(environment: Environment): String = {
    val blobsByLocation = environment.blobs.groupBy(_.location).mapValues(_.head)

    val locationContents = for {
      y <- 0 until environment.height
      x <- 0 until environment.width
    } yield {
      val location = Location(x, y)
      if (blobsByLocation.contains(location)) blobsByLocation(location).size.toString.padTo(3, " ").mkString
      else " . "
    }

    locationContents
      .grouped(environment.width)
      .map(_.mkString)
      .mkString("\n")
  }

  def cursorUp(n: Int): String = {
    s"\033[${n}A"
  }

  def randomBlob(width: Int, height: Int, maxSize: Int): Blob = {
    Blob(Location(Random.nextInt(width), Random.nextInt(height)), Random.nextInt(maxSize - 1) + 1)
  }

  def main(args: Array[String]): Unit = {
    val environment = randomEnvironment()

    val states = Iterator.iterate(environment)(updateState)
      .sliding(2)
      .takeUntil { case Seq(prev, next) => next == prev }
      .map(_.head)

    states.foreach(x => {
      println(environmentToString(x))
      Thread.sleep(100)
      println(cursorUp(environment.height + 1))
    })
  }

  private def predefinedEnvironment(): Environment = {
    Environment(10, 5, List(
      Blob(Location(0, 1), 5),
      Blob(Location(2, 4), 2),
      Blob(Location(4, 3), 1),
      Blob(Location(8, 3), 1)
    ))
  }

  def randomEnvironment(): Environment = {
    val width = 60
    val height = 40
    val numberOfBlobs = 100
    val maxSize = 5

    val blobs = Iterator.continually(randomBlob(width, height, maxSize)).take(numberOfBlobs).toList

    Environment(width, height, blobs)
  }
}
