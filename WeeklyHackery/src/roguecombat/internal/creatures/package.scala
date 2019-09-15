package roguecombat.internal

import roguecombat.internal.attributes.{CanAttack, Health, HealthUpdater}

package object creatures {

  case class Goblin(health: Int = 8,
                    maxHealth: Int = 8,
                    attack: Int = 4)
    extends Health[Goblin]
      with CanAttack {

    override val healthUpdater: HealthUpdater[Goblin] = new HealthUpdater[Goblin] {
      override def withHealth(x: Int): Goblin = Goblin.this.copy(health = x)
    }
  }

  case class Human(health: Int = 20,
                   maxHealth: Int = 20,
                   attack: Int = 6)
    extends Health[Human]
      with CanAttack {

    override val healthUpdater: HealthUpdater[Human] = new HealthUpdater[Human] {
      override def withHealth(x: Int): Human = Human.this.copy(health = x)
    }
  }
}
