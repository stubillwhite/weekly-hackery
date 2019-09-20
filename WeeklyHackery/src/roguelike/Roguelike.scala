package roguelike

import roguelike.internal.creatures
import roguelike.internal.creatures.{Combat, Combatant, Goblin, Orc, Player, resolveCombat}

import scala.io.StdIn
import scala.util.Random

object Roguelike {

  def lootCorpse(player: Player): Unit = {
    val gold = Random.nextInt(5)
    println(s"Found ${gold} gold!")
    player.addItems("Gold", gold)
  }

  def randomOpponent(): Combatant = {
    Random.shuffle(List(new Orc(), new Goblin())).head
  }

  def gameLoop(player: Player): Unit = {
    val combat = Combat(player, randomOpponent())
    println(s"You encounter: ${combat.opponent.getClass.getSimpleName}")
    println("Combat ensues!")

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
