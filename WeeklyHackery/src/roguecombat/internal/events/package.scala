package roguecombat.internal

import roguecombat.internal.attributes.Combatant

import scala.annotation.tailrec

package object events {
  case class Combat[A <: Combatant[A], B <: Combatant[B]](player: A, enemy: B) {
    @tailrec
    final def resolve(): Combat[A, B] = {
        println(this)

        val newEnemy = enemy.takeDamage(player.attack)
        val newPlayer = player.takeDamage(enemy.attack)

        if (newEnemy.health == 0) copy(enemy = newEnemy)
        else copy(player = newPlayer, enemy = newEnemy).resolve()
    }
  }
}
