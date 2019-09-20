package roguecombat.internal

import roguecombat.internal.attributes.{CanAttack, Combatant, HasHealth}

import scala.annotation.tailrec
import scala.util.Random

package object events {

  case class Combat[A <: Combatant[A], B <: Combatant[B]](player: A, enemy: B) {

    private def attack[T <: HasHealth[T]](attacker: CanAttack, defender: T): T = {
      if (Random.nextInt(100) <= attacker.accuracy) {
        println(s"${attacker.getClass.getSimpleName} hits!")
        defender.takeDamage(attacker.attack)
      }
      else {
        println(s"${attacker.getClass.getSimpleName} misses!")
        defender
      }
    }

    @tailrec
    final def resolve(): Combat[A, B] = {
      val newEnemy = attack(player, enemy)

      if (newEnemy.health == 0) {
        copy(enemy = newEnemy)
      }
      else {
        val newPlayer = attack(enemy, player)
        val newCombat = copy(player = newPlayer, enemy = newEnemy)

        if (newCombat.player.health == 0) newCombat
        else newCombat.resolve()
      }
    }
  }
}
