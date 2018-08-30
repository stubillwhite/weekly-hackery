package labyrinthsareawesome.internal.generator

import labyrinthsareawesome.internal.display.LabyrinthRenderer
import labyrinthsareawesome.internal.{Labyrinth, Passage, Room}

import scala.util.Random

object RecursiveBacktracker {

  def generateLabyrinth(width: Int, height: Int, displayProgress: Boolean = false): Labyrinth = {
    val start = randomRoom(width, height)
    val end = randomRoom(width, height)

    val initialState = State(width, height, start, end, Set.empty, Set(start), List(start), false)

    val displayFunction = if (displayProgress) displayStateAndPause else noOperation

    val finalState =
      Stream.iterate(initialState)(nextState)
        .map(displayFunction)
        .dropWhile(_.isComplete == false)
        .head

    toLabyrinth(finalState)
  }

  private def randomRoom(width: Int, height: Int): Room = {
    Room(Random.nextInt(width), Random.nextInt(height))
  }

  private val displayStateAndPause: (State) => State = (state: State) => {
    val characterDisplay = LabyrinthRenderer.render(toLabyrinth(state))
    println(characterDisplay.render() + s"\033[${characterDisplay.height}A")
    Thread.sleep(25)
    state
  }

  private val noOperation: State => State = (state: State) => state

  private def toLabyrinth(state: RecursiveBacktracker.State): Labyrinth = {
    Labyrinth(state.width, state.height, state.passages, state.start, state.end)
  }

  case class State(width: Int,
                   height: Int,
                   start: Room,
                   end: Room,
                   passages: Set[Passage],
                   visited: Set[Room],
                   path: List[Room],
                   isComplete: Boolean)

  def nextState(state: State): State = {

    nextPointOnPathWithUnvisitedNeighbours(state) match {
      case None =>
        state.copy(isComplete = true)

      case Some(room) =>
        val newRoom = selectRandomUnvisitedRoomNeighbouring(state, room)

        val newPassages = state.passages + Passage(room, newRoom)
        val newVisited = state.visited + newRoom
        val newPath = newRoom :: state.path

        state.copy(passages = newPassages, visited = newVisited, path = newPath)
    }
  }

  private def selectRandomUnvisitedRoomNeighbouring(state: State, room: Room): Room = {
    Random.shuffle(unvisitedNeighbours(state, room).toList).head
  }

  private def nextPointOnPathWithUnvisitedNeighbours(state: State): Option[Room] = {
    state.path.dropWhile(unvisitedNeighbours(state, _).isEmpty).headOption
  }

  private def isWithinBounds(state: State, room: Room): Boolean = {
    (0 <= room.x && room.x < state.width) && (0 <= room.y && room.y < state.height)
  }

  private def neighbours(state: State, room: Room): Set[Room] = {
    val deltas = Set(Room(0, -1), Room(-1, 0), Room(1, 0), Room(0, 1))
    deltas.map(_ + room).filter(isWithinBounds(state, _))
  }

  private def unvisitedNeighbours(state: State, room: Room): Set[Room] = {
    neighbours(state, room).diff(state.visited)
  }
}
