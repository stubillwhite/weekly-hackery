package labyrinthsareawesome.internal.display

import labyrinthsareawesome.internal.{Labyrinth, Passage, Room}

object LabyrinthRenderer {

  private val room =
    """+---+
      ||   |
      |+---+""".stripMargin

  private val roomWidth = room.split("\n").head.length
  private val roomHeight = room.split("\n").length

  def apply(labyrinth: Labyrinth): LabyrinthRenderer = {
    val width = labyrinth.width
    val height = labyrinth.height
    val display = CharacterDisplay(width * (roomWidth - 1) + 1, height * (roomHeight - 1) + 1)
    LabyrinthRenderer(labyrinth, display)
      .withRooms()
      .withPassages()
      .withRoomContent(labyrinth.start.x, labyrinth.start.y, "S")
      .withRoomContent(labyrinth.end.x, labyrinth.end.y, "E")
  }

  case class LabyrinthRenderer(labyrinth: Labyrinth,
                               display: CharacterDisplay) {


    def withRooms(): LabyrinthRenderer = {
      val offsets: Seq[(Int, Int)] = for {
        x <- Range(0, labyrinth.width)
        y <- Range(0, labyrinth.height)
      } yield (x * (roomWidth - 1), y * (roomHeight - 1))

      val newDisplay = offsets.foldLeft(display) { case (d, (x, y)) => d.draw(x, y, room) }
      copy(display = newDisplay)
    }

    def withPassages(): LabyrinthRenderer = {
      val newDisplay = labyrinth.passages.foldLeft(display) {
        case (d, Passage(Room(x1, y1), Room(x2, y2))) =>
          if (x1 == x2) {
            val x = (x1 * (roomWidth - 1)) + 1
            val y = Math.max(y1, y2) * (roomHeight - 1)
            d.draw(x, y, "   ")
          }
          else {
            val x = Math.max(x1, x2) * (roomWidth - 1)
            val y = (y1 * (roomHeight - 1)) + 1
            d.draw(x, y, " ")
          }
      }

      copy(display = newDisplay)
    }

    def withRoomContent(x: Int, y: Int, content: String): LabyrinthRenderer = {
      val newDisplay = display.draw(x * (roomWidth - 1) + 2, y * (roomHeight - 1) + 1, content)
      copy(display = newDisplay)
    }

    def toCharacterDisplay(): CharacterDisplay = {
      display
    }
  }
}
