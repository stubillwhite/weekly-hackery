package gameofblobs.internal

package object domain {

  private val clockwiseDeltaOrdering: List[Location] = List(
    Location(0, -1), // N
    Location(1, -1), // NE
    Location(1, 0), // E
    Location(1, 1), // SE
    Location(0, 1), // S
    Location(-1, 1), // SW
    Location(-1, 0), // W
    Location(-1, -1) // NW
  )

  case class Location(x: Int, y: Int) extends Ordered[Location] {
    override def compare(that: Location): Int = {
      clockwiseDeltaOrdering.indexOf(this).compareTo(clockwiseDeltaOrdering.indexOf(that))
    }
  }

  case class Blob(location: Location, size: Long)

  case class Environment(width: Int, height: Int, blobs: List[Blob])

  def distance(a: Location, b: Location): Double = {
    val squareOf = (x: Int) => Math.pow(x, 2)
    Math.sqrt(squareOf(a.x - b.x) + squareOf(a.y - b.y))
  }

  def nearestPrey(predator: Blob, blobs: List[Blob]): Option[Blob] = {
    blobs.view
      .filter(x => x != predator)
      .filter(x => predator.size > x.size)
      .sortBy(x => distance(predator.location, x.location))
      .headOption
  }

  def moveDelta(a: Int, b: Int): Int = {
    if (a > b) -1
    else if (a < b) +1
    else 0
  }

  def newLocation(predatorLocation: Location, preyLocation: Location): Location = {
    Location(
      predatorLocation.x + moveDelta(predatorLocation.x, preyLocation.x),
      predatorLocation.y + moveDelta(predatorLocation.y, preyLocation.y)
    )
  }

  def mergeBlobs(blobs: List[Blob]): List[Blob] = {
    blobs
      .groupBy(_.location)
      .map { case (k, vs) => Blob(k, vs.map(_.size).sum) }
      .toList
  }

  def moveBlobs(blobs: List[Blob]): List[Blob] = {
    def moveBlob(blob: Blob): Blob = {
      val potentialPrey = nearestPrey(blob, blobs)
      potentialPrey match {
        case Some(prey) => blob.copy(location = newLocation(blob.location, prey.location))
        case None => blob
      }
    }

    blobs.map(moveBlob)
  }

  def updateState(environment: Environment): Environment = {
    val newBlobs = mergeBlobs(moveBlobs(environment.blobs))
    environment.copy(blobs = newBlobs)
  }
}
