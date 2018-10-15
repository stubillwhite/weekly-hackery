package playthedragon.internal

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FunSuite, Matchers}
import playthedragon.internal.Action._
import playthedragon.internal.Fighter._

class GameTreeExplorerTest extends FunSuite with Matchers with MockitoSugar {
  private val (dragonHealth, dragonMaxHealth, dragonAttack, knightHealth, knightAttack, buffModifier, debuffModifier) = (10, 10, 3, 10, 3, 2, 2)
  private val gameState = GameState(dragonHealth, dragonMaxHealth, dragonAttack, knightHealth, knightAttack, buffModifier, debuffModifier)

  test("findWinningActions given unwinnable fight then unwinnable") {
    // Given
    val initialState = gameState.copy(knightAttack = dragonMaxHealth + 1)

    // When
    GameTreeExplorer.findWinningActions(initialState) shouldBe None
  }

  test("findWinningActions given trivially winnable fight then just attack") {
    // Given
    val initialState = gameState.copy(knightHealth = dragonAttack * 2)

    // When
    GameTreeExplorer.findWinningActions(initialState) shouldBe Some(List(Attack, Attack))
  }
}
