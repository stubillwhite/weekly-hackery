package roguecombat

import roguecombat.internal.Domain.Game
import roguecombat.internal.attributes.Health
import roguecombat.internal.creatures.{Goblin, Human, Porg}

object RogueCombat {

  def newGame(): Game = {
    Game(Human(), Goblin())
  }

  def nextState(game: Game): Game = {
    val newMonster = game.monster match {
      case x: Goblin => x.takeDamage(game.player.attack)
    }
    val newGame = game.copy(monster = newMonster)
    println(newGame)
    newGame
  }

  def main(args: Array[String]): Unit = {
    println(nextState(newGame()))
    println(Porg(20, 10))
    println(Porg(20, 10).takeDamage(13))
    println(Porg(20, 10).takeDamage(13).healCompletely())
    println("Done")
  }
}
