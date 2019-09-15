package roguecombat.internal

import Math.max

package object attributes {

  trait Health[A] {
    val health: Int
    val maxHealth: Int
    val healthUpdater: HealthUpdater[A]

    def healCompletely(): A = {
      healthUpdater.withHealth(maxHealth)
    }

    def takeDamage(points: Int): A = {
      healthUpdater.withHealth(max(0, health - points))
    }
  }

  trait HealthUpdater[A] {
    def withHealth(x: Int): A
  }

  trait CanAttack {
    val attack: Int
  }
}
