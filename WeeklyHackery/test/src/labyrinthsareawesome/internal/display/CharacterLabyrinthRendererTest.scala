package labyrinthsareawesome.internal.display

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class CharacterLabyrinthRendererTest extends FlatSpec with Matchers with MockitoSugar {

  behavior of "render"

  it should "render a single items correctly" in {
    // Given
    val display = CharacterDisplay(3, 3)
      .draw(0, 0, "1")
      .draw(1, 0, "2")
      .draw(2, 0, "3")
      .draw(0, 1, "4")
      .draw(1, 1, "5")
      .draw(2, 1, "6")
      .draw(0, 2, "7")
      .draw(1, 2, "8")
      .draw(2, 2, "9")

    // When, Then
    display.render() should be(
      """123
        |456
        |789""".stripMargin)
  }

  it should "render multi-line items correctly" in {
    // Given
    val display = CharacterDisplay(3, 3)
      .draw(0, 0, "1\n4\n7\n")
      .draw(1, 0, "2\n5\n8\n")
      .draw(2, 0, "3\n6\n9\n")

    // When, Then
    display.render() should be(
      """123
        |456
        |789""".stripMargin)
  }

  it should "render offset items correctly" in {
    // Given
    val display = CharacterDisplay(3, 3)
      .draw(1, 1, """123
                    |456
                    |789""".stripMargin)

    println(display.render())

    // When, Then
    display.render() should be(
      """
        | 12
        | 45""".stripMargin)
  }

  it should "overwrite content" in {
    // Given
    val display = CharacterDisplay(1, 1)
      .draw(0, 0, "1")
      .draw(0, 0, "2")

    // When, Then
    display.render() should be(
      """2""".stripMargin)
  }
}
