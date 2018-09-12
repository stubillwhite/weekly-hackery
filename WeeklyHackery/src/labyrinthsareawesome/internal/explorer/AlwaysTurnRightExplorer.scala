package labyrinthsareawesome.internal.explorer

import labyrinthsareawesome.internal.display.LabyrinthRenderer
import labyrinthsareawesome.internal.{Labyrinth, Passage, Room}

object AlwaysTurnRightExplorer {

  def escapeLabyrinth(labyrinth: Labyrinth, displayProgress: Boolean = false): Unit = {
    new AlwaysTurnRightExplorer(labyrinth).escapeLabyrinth(displayProgress)
  }
}

class AlwaysTurnRightExplorer(labyrinth: Labyrinth) {

  def escapeLabyrinth(displayProgress: Boolean = false): Unit = {

    val initialState = State(labyrinth.start, Orientations.North, labyrinth.end, false)

    val displayFunction = if (displayProgress) displayStateAndPause else noOperation

    val finalState =
      Stream.iterate(initialState)(nextState)
        .map(displayFunction)
        .dropWhile(_.isComplete == false)
        .head
  }

  private val displayStateAndPause: (State) => State = (state: State) => {
    val explorerSymbol =
      state.orientation match {
        case Orientations.North => "^"
        case Orientations.East => ">"
        case Orientations.South => "v"
        case Orientations.West => "<"
      }

    val characterDisplay = LabyrinthRenderer(labyrinth)
      .withRoomContent(state.location.x, state.location.y, explorerSymbol).toCharacterDisplay()

    println(characterDisplay.render() + s"\033[${characterDisplay.height}A")
    Thread.sleep(100)
    state
  }

  private val noOperation: State => State = (state: State) => state

  object Orientations extends Enumeration {
    type Orientation = Value
    val North, East, South, West = Value
  }

  import Orientations.Orientation

  case class State(location: Room,
                   orientation: Orientation,
                   end: Room,
                   isComplete: Boolean) {

    def rotateRight(): State = {
      val newOrientation = orientation match {
        case Orientations.North => Orientations.East
        case Orientations.East => Orientations.South
        case Orientations.South => Orientations.West
        case Orientations.West => Orientations.North
      }
      copy(orientation = newOrientation)
    }

    def rotateLeft(): State = {
      rotateRight().rotateRight().rotateRight()
    }

    def moveForward(): State = {
      val newLocation = roomAhead()
      copy(location = newLocation)
    }

    def roomsAccessibleFromCurrentRoom(): Set[Room] = {
      labyrinth.passages.flatMap({
        case Passage(exit, this.location) => Some(exit)
        case Passage(this.location, exit) => Some(exit)
        case _ => None
      })
    }

    def roomAhead(): Room = {
      val deltas = List(
        Orientations.North -> Room(0, -1),
        Orientations.East -> Room(1, 0),
        Orientations.South -> Room(0, 1),
        Orientations.West -> Room(-1, 0)).toMap
      location + deltas(orientation)
    }
  }

  def nextState(state: State): State = {
    if (state.location == state.end) {
      state.copy(isComplete = true)
    }
    else {
      if (canMoveRight(state)) {
        state.rotateRight().moveForward()
      }
      else if (canMoveForward(state)) {
        state.moveForward()
      }
      else {
        state.rotateLeft()
      }
    }
  }

  private def canMoveRight(state: State): Boolean = {
    val newLocation = state.rotateRight().moveForward().location
    state.roomsAccessibleFromCurrentRoom().contains(newLocation)
  }


  private def canMoveForward(state: State): Boolean = {
    val newLocation = state.moveForward().location
    state.roomsAccessibleFromCurrentRoom().contains(newLocation)
  }
}
