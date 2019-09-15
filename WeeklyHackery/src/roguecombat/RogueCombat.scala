package roguecombat

import roguecombat.internal.creatures.{Human, Orc}
import roguecombat.internal.events.Combat

object RogueCombat {

  def newCombat(): Combat[_, _] = {
    Combat(Human(), Orc())
  }

  def main(args: Array[String]): Unit = {
    val combat = newCombat()
    println(combat.resolve())
    println("Done")
  }
}
