package labyrinthsareawesome.internal.display

import labyrinthsareawesome.internal.{Labyrinth, Passage, Room}

object LabyrinthRenderer {

  private val room =
    """+---+
      ||   |
      |+---+""".stripMargin

  private val roomWidth = room.split("\n").head.length
  private val roomHeight = room.split("\n").length

  def render(labyrinth: Labyrinth): CharacterDisplay = {
    val width = labyrinth.width
    val height = labyrinth.height
    val display = CharacterDisplay(width * (roomWidth - 1) + 1, height * (roomHeight - 1) + 1)

    val renderPipeline =
      drawRooms(width, height)
        .andThen(drawPassages(labyrinth.passages))
          .andThen(drawStart(labyrinth.start))
          .andThen(drawEnd(labyrinth.end))

    renderPipeline(display)
  }

  type DisplayPipelineStage = (CharacterDisplay) => CharacterDisplay

  private def drawRooms(width: Int, height: Int): DisplayPipelineStage = {
    val offsets: Seq[(Int, Int)] = for {
      x <- Range(0, width)
      y <- Range(0, height)
    } yield (x * (roomWidth - 1), y * (roomHeight - 1))

    (display: CharacterDisplay) => {
      offsets.foldLeft(display) { case (disp, (x, y)) => disp.draw(x, y, room) }
    }
  }

  private def drawPassages(passages: Set[Passage]): DisplayPipelineStage = {
    (display: CharacterDisplay) => {
      passages.foldLeft(display) {
        case (disp, Passage(Room(x1, y1), Room(x2, y2))) =>
          if (x1 == x2) {
            val x = (x1 * (roomWidth - 1)) + 1
            val y = Math.max(y1, y2) * (roomHeight - 1)
            disp.draw(x, y, "   ")
          }
          else {
            val x = Math.max(x1, x2) * (roomWidth - 1)
            val y = (y1 * (roomHeight - 1)) + 1
            disp.draw(x, y, " ")
          }
      }
    }
  }

  private def drawStart(room: Room): DisplayPipelineStage = {
    (display: CharacterDisplay) => {
      setRoomContent(display, room.x, room.y, "S")
    }
  }

  private def drawEnd(room: Room): DisplayPipelineStage = {
    (display: CharacterDisplay) => {
      setRoomContent(display, room.x, room.y, "E")
    }
  }

  private def setRoomContent(display: CharacterDisplay, x: Int, y: Int, content: String): CharacterDisplay = {
    display.draw(x * (roomWidth - 1) + 2, y * (roomHeight - 1) + 1, content)
  }
}

