package roguelike

import roguelike.internal.creatures
import roguelike.internal.creatures.{Combat, Goblin, Player, resolveCombat}

import scala.io.StdIn
import scala.util.Random

object Roguelike {

  def lootCorpse(player: Player): Unit = {
    val gold = Random.nextInt(5)
    println(s"Found ${gold} gold!")
    player.addItems("Gold", gold)
  }

  def gameLoop(player: Player): Unit = {
    println("Combat ensues!")

    val combat = Combat(player, new Goblin())
    resolveCombat(combat)

    if (combat.player.health == 0) {
      println("You died!")
    }
    else {
      lootCorpse(combat.player)

      println()
      println(s"Player health: ${player.healthStat()}, loot: ${player.inventory.getOrElse("Gold", 0)} gold")
      println()
      println("[C]ontinue or [E]xit")
      StdIn.readChar() match {
        case 'c' => gameLoop(player)
        case 'e' => println(s"You escaped with ${combat.player.inventory.getOrElse("Gold", 0)} gold")
      }
    }
  }

  def main(args: Array[String]): Unit = {
    gameLoop(new Player())
    println("Done")
  }
}
