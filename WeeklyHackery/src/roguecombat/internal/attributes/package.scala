package roguecombat.internal

import java.lang.Math.max

package object attributes {

  trait HasHealth[A] {
    val health: Int
    val maxHealth: Int

    def healCompletely(): A = {
      withHealth(maxHealth)
    }

    def takeDamage(points: Int): A = {
      withHealth(max(0, health - points))
    }

    protected def withHealth(x: Int): A
  }

  trait CanAttack {
    val attack: Int
  }

  trait Combatant[A] extends HasHealth[A] with CanAttack
}