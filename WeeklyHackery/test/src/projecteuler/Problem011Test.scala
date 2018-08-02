package projecteuler

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import projecteuler.Problem011.IndexedGrid

class Problem011Test extends FlatSpec with Matchers with MockitoSugar {

  private val testGrid =
    """0 1 2
      |3 4 5
      |6 7 8
    """.stripMargin

  behavior of "grid"

  it should "access values by coordinate" in {
    // Given, When
    val indexedGrid = IndexedGrid.fromString(3, 3, testGrid)

    // Then
    indexedGrid.grid((0, 0)) should be(0)
    indexedGrid.grid((0, 2)) should be(6)
    indexedGrid.grid((2, 0)) should be(2)
    indexedGrid.grid((2, 2)) should be(8)
  }

  behavior of "allAdjacentValues"

  it should "generate windows for all adjacent values" in {
    // Given
    val coords = for {
      x <- 0 to 2
      y <- 0 to 2
    } yield (x, y)

    // When
    val indexedGrid = IndexedGrid.fromString(3, 3, testGrid)
    val allAdjacent = indexedGrid.allAdjacentValues(3)

    // Then
    allAdjacent should contain theSameElementsAs Set(
      Set(0, 1, 2),
      Set(3, 4, 5),
      Set(6, 7, 8),

      Set(0, 3, 6),
      Set(1, 4, 7),
      Set(2, 5, 8),

      Set(0, 4, 8),
      Set(6, 4, 2)
    )
  }
}
