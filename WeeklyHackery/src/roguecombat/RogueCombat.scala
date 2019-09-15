package roguecombat

import roguecombat.internal.Domain.Game
import roguecombat.internal.creatures.{Goblin, Human}

object RogueCombat {

  def newGame(): Game = {
    Game(Human(), Goblin())
  }

  def nextState(game: Game): Game = {
   println(game)
    val Game(player, goblin) = game

    val newGoblin = goblin.takeDamage(player.attack)
    val newPlayer = player.takeDamage(goblin.attack)

    if (newGoblin.health == 0) game.copy(goblin = newGoblin)
    else nextState(game.copy(player = newPlayer, goblin = newGoblin))
  }

  def main(args: Array[String]): Unit = {
    println(nextState(newGame()))
    println("Done")
  }
}
