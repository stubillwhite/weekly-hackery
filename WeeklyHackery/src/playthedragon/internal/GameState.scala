package playthedragon.internal

import java.lang.Math.max

import playthedragon.internal.Action._
import playthedragon.internal.Fighter._

case class GameState(dragonHealth: Long,
                     dragonMaxHealth: Long,
                     dragonAttack: Long,
                     knightHealth: Long,
                     knightAttack: Long,
                     buffModifier: Long,
                     debuffModifier: Long) {

  def knightIsDead: Boolean =
    knightHealth == 0

  def dragonIsDead: Boolean =
    dragonHealth == 0

  def takeAction(fighter: Fighter,
                 action: Action): GameState = {
    fighter match {
      case Dragon =>
        if (dragonIsDead) this else dragonTakeAction(action)

      case Knight =>
        if (knightIsDead) this else knightTakeAction(action)
    }
  }

  override def toString: String =
    s"[Hd: $dragonHealth, Ad: $dragonAttack, Hk: $knightHealth, Ak: $knightAttack, B: $buffModifier, D: $debuffModifier]"

  private def dragonTakeAction(action: Action): GameState = {
    action match {
      case Attack =>
        copy(knightHealth = max(0, knightHealth - dragonAttack))

      case Buff =>
        copy(dragonAttack = dragonAttack + buffModifier)

      case Cure =>
        copy(dragonHealth = dragonMaxHealth)

      case Debuff =>
        copy(knightAttack = max(0, knightAttack - debuffModifier))
    }
  }

  private def knightTakeAction(action: Action): GameState = {
    action match {
      case Attack =>
        copy(dragonHealth = max(0, dragonHealth - knightAttack))
    }
  }
}
