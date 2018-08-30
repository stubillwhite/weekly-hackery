package labyrinthsareawesome.internal.display

object CharacterDisplay {
  def apply(width: Int, height: Int): CharacterDisplay = {
    CharacterDisplay(width, height, Map.empty).clear()
  }
}

case class CharacterDisplay(width: Int, height: Int, content: Map[(Int, Int), Char]) {

  def clear(): CharacterDisplay = {
    val newContent = (for {
      x <- 0 until width
      y <- 0 until height
    } yield (x, y) -> ' ').toMap

    copy(content = newContent)
  }

  def draw(x: Int, y: Int, item: String): CharacterDisplay = {
    copy(content = content ++ itemToMap(x, y, item))
  }

  private def itemToMap(dx: Int, dy: Int, item: String): Map[(Int, Int), Char] = {
    val itemChars = item
      .split("\n")
      .map(_.toCharArray)

    val itemAsMap = (for {
      y <- itemChars.indices
      x <- itemChars(0).indices
    } yield (x + dx, y + dy) -> itemChars(y)(x)).toMap

    itemAsMap.filterKeys(isWithinBounds)
  }

  private def isWithinBounds(point: (Int, Int)): Boolean = {
    point match {
      case (x, y) => (0 <= x && x < width) && (0 <= y && y < height)
    }
  }

  def render(): String = {
    val charSeq = for {
      y <- 0 until height
      x <- 0 until width
    } yield content(x, y)

    charSeq
      .sliding(width, width)
      .map(_.mkString(""))
      .mkString("\n")
  }
}
