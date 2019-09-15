package roguecombat.internal

import roguecombat.internal.attributes.{CanAttack, Health, HealthUpdater, Monster}

package object creatures {

  case class Porg(health: Int,
                  maxHealth: Int)
    extends Health[Porg] {

    override val healthUpdater: HealthUpdater[Porg] = new HealthUpdater[Porg] {
      override def withHealth(x: Int): Porg = Porg.this.copy(health = x)
    }
  }

  case class Goblin(health: Int = 8,
                    maxHealth: Int = 8,
                    attack: Int = 4)
    extends Monster
      with Health[Goblin]
      with CanAttack {

    override val healthUpdater: HealthUpdater[Goblin] = new HealthUpdater[Goblin] {
      override def withHealth(x: Int): Goblin = Goblin.this.copy(health = x)
    }
  }

  case class Human(health: Int = 20,
                   maxHealth: Int = 20,
                   attack: Int = 10)
    extends Monster
      with Health[Human]
      with CanAttack {

    override val healthUpdater: HealthUpdater[Human] = new HealthUpdater[Human] {
      override def withHealth(x: Int): Human = Human.this.copy(health = x)
    }
  }

}
