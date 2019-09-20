package roguelike.internal

import java.lang.Math.max

import scala.util.Random

package object creatures {

  trait Inventory {
    var inventory: Map[String, Int]

    def addItems(key: String, count: Int): Unit = {
      inventory += (key -> (inventory.getOrElse(key, 0) + count))
    }

    def removeItem(key: String): Unit = {
      inventory += (key -> (inventory.getOrElse(key, 0) - 1))
      if (inventory(key) <= 0) inventory -= key
    }
  }

  trait HasHealth {
    var health: Int
    val maxHealth: Int

    def healCompletely(): Unit = {
      withHealth(maxHealth)
    }

    def takeDamage(points: Int): Unit = {
      withHealth(max(0, health - points))
    }

    def healthStat(): String = {
      s"[${health}/${maxHealth}]"
    }

    protected def withHealth(x: Int): Unit
  }

  trait CanAttack {
    val attack: Int
    val accuracy: Int
  }

  trait Combatant extends HasHealth with CanAttack

  class Goblin extends Combatant {
    override val attack: Int = 6
    override val accuracy: Int = 50
    override var health: Int = 10
    override val maxHealth: Int = 10

    override protected def withHealth(x: Int): Unit =
      health = x
  }

  class Orc extends Combatant {
    override val attack: Int = 8
    override val accuracy: Int = 50
    override var health: Int = 15
    override val maxHealth: Int = 15

    override protected def withHealth(x: Int): Unit =
      health = x
  }

  class Player extends Combatant with Inventory {
    override val attack: Int = 8
    override val accuracy: Int = 75
    override var health: Int = 25
    override val maxHealth: Int = 25
    override var inventory: Map[String, Int] = Map()

    override protected def withHealth(x: Int): Unit =
      health = x


  }

  case class Combat(player: Player, opponent: Combatant)

  def attack(attacker: Combatant, defender: Combatant): Unit = {
    val attackerName = attacker.getClass.getSimpleName
    val defenderName = defender.getClass.getSimpleName

    if (Random.nextInt(100) <= attacker.accuracy) {
      defender.takeDamage(attacker.attack)
      println(s"${attackerName} hits! (${defenderName} ${defender.healthStat()})")
    }
    else {
      println(s"${attackerName} misses! (${defenderName} ${defender.healthStat()})")
      defender
    }
  }

  def resolveCombat(combat: Combat): Unit = {
    val Combat(player, enemy) = combat

    attack(player, enemy)

    if (enemy.health > 0) {
      attack(enemy, player)
      if (player.health > 0) {
        resolveCombat(combat)
      }
    }
  }
}
