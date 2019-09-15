package roguecombat.internal

import roguecombat.internal.attributes.Combatant

package object creatures {

  case class Orc(health: Int = 8,
                    maxHealth: Int = 8,
                    attack: Int = 4)
    extends Combatant[Orc] {
    override protected def withHealth(x: Int): Orc = copy(health = x)
  }

  case class Goblin(health: Int = 6,
                 maxHealth: Int = 6,
                 attack: Int = 3) extends Combatant[Goblin] {
    override protected def withHealth(x: Int): Goblin = copy(health = x)
  }

  case class Human(health: Int = 20,
                   maxHealth: Int = 20,
                   attack: Int = 6)
    extends Combatant[Human] {
    override protected def withHealth(x: Int): Human = copy(health = x)
  }
}
