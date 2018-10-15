package playthedragon.internal

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FunSuite, Matchers}
import playthedragon.internal.Action._
import playthedragon.internal.Fighter._

class GameStateTest extends FunSuite with Matchers with MockitoSugar {

  private val (dragonHealth, dragonMaxHealth, dragonAttack, knightHealth, knightAttack, buffModifier, debuffModifier) = (8, 10, 5, 10, 5, 2, 3)
  private val initialState = GameState(dragonHealth, dragonMaxHealth, dragonAttack, knightHealth, knightAttack, buffModifier, debuffModifier)

  test("takeAction given dragon attack then damages knight") {
    initialState.takeAction(Dragon, Attack) shouldBe initialState.copy(knightHealth = knightHealth - dragonAttack)
  }

  test("takeAction given dragon buff then increases dragon attack") {
    initialState.takeAction(Dragon, Buff) shouldBe initialState.copy(dragonAttack = dragonAttack + buffModifier)
  }

  test("takeAction given dragon cure then raises dragon health to maximum") {
    initialState.takeAction(Dragon, Cure) shouldBe initialState.copy(dragonHealth = dragonMaxHealth)
  }

  test("takeAction given dragon debuff then decreases knight attack") {
    initialState.takeAction(Dragon, Debuff) shouldBe initialState.copy(knightAttack = knightAttack - debuffModifier)
  }

  test("takeAction given knight attack then damages dragon") {
    initialState.takeAction(Knight, Attack) shouldBe initialState.copy(dragonHealth = dragonHealth - knightAttack)
  }
}
