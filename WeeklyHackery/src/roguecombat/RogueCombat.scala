package roguecombat

import roguecombat.internal.attributes.Combatant
import roguecombat.internal.creatures.{Goblin, Human, Orc, Player}
import roguecombat.internal.events.Combat

import scala.util.Random

object RogueCombat {

  def randomCombatant[T <: Combatant[T]](): T = {
    val combatants = List(Human(), Orc(), Goblin())
    Random.shuffle(combatants).head.asInstanceOf[T] // TODO: What?
  }

  def newCombat(): Combat[_, _] = {
    Combat(Player(), randomCombatant())
  }

  def main(args: Array[String]): Unit = {
    val combat = newCombat()
    println(combat.resolve())
    println("Done")
  }
}
