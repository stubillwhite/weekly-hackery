package labyrinthsareawesome.internal.generator

import labyrinthsareawesome.internal.{Passage, Room}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class RecursiveBacktrackerTest extends FlatSpec with Matchers with MockitoSugar {

  private val room_0_0 = Room(0, 0)
  private val room_0_1 = Room(0, 1)
  private val room_0_2 = Room(0, 2)
  private val room_1_0 = Room(1, 0)
  private val room_1_1 = Room(1, 1)
  private val room_1_2 = Room(1, 2)
  private val room_2_0 = Room(2, 0)
  private val room_2_1 = Room(2, 1)
  private val room_2_2 = Room(2, 2)

  private val allRooms = Set(
    room_0_0, room_1_0, room_2_0,
    room_0_1, room_1_1, room_2_1,
    room_0_2, room_1_2, room_2_2)


  behavior of "nextState"

  it should "select the next valid neighbour if neighbours are out of bounds" in {

    // Given
    val state = RecursiveBacktracker.State(1, 3, Set.empty, Set.empty, List(room_0_0), false)

    // When
    val nextState = RecursiveBacktracker.nextState(state)

    // Then
    nextState should be(state.copy(
      passages = state.passages + Passage(room_0_0, room_0_1),
      visited = state.visited + room_0_1,
      path = room_0_1 :: state.path))
  }

  it should "select the next valid neighbour if neighbours have been visited" in {

    // Given
    val state = RecursiveBacktracker.State(3, 3, Set.empty, allRooms - room_1_1, List(room_1_0), false)

    // When
    val nextState = RecursiveBacktracker.nextState(state)

    // Then
    nextState should be(state.copy(
      passages = state.passages + Passage(room_1_0, room_1_1),
      visited = state.visited + room_1_1,
      path = room_1_1 :: state.path))
  }
}
