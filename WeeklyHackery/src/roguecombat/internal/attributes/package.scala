package roguecombat.internal

package object attributes {

  trait Monster

  trait Health[A] {
    this: A =>
    val health: Int
    val maxHealth: Int
    val healthUpdater: HealthUpdater[A]

    def healCompletely(): A = {
      healthUpdater.withHealth(maxHealth)
    }

    def takeDamage(points: Int): A = {
      healthUpdater.withHealth(health - points)
    }
  }

  trait HealthUpdater[A] {
    def withHealth(x: Int): A
  }

  trait CanAttack {
    val attack: Int
  }
}
