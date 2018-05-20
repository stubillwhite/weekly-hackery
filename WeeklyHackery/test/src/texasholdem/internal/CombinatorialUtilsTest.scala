package texasholdem.internal

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import texasholdem.internal.CombinatorialUtils._

class CombinatorialUtilsTest extends FlatSpec with Matchers with MockitoSugar {

  behavior of "selectWithoutReplacement"

  it should "generate all ways of selecting N items from M without replacement" in {
    // Given, When
    val result = selectWithoutReplacement(2, (1 to 3).toList)

    // Then
    result should contain theSameElementsAs List(List(1, 2), List(2, 3), List(1, 3))
  }
}
