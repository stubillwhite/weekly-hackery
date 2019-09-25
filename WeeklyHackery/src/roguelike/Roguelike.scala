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
    println(s"You encounter: ${combat.opponent.longDescription}")
    println("Combat ensues!")

    resolveCombat(combat)

    if (combat.player.health == 0) {
      println("You died!")
    }
    else {
      println(s"You defeated the ${combat.opponent.shortDescription}!")
      lootCorpse(combat.player)

      println()
      println(s"Player health: ${player.healthStat()}, loot: ${player.inventory.getOrElse("Gold", 0)} gold")
      println()

      println("[C]ontinue, [Heal], or [E]xit")
      StdIn.readChar() match {
        case 'c' => {
          optionallyHeal(player)
          gameLoop(player)
        }
        case 'e'
        => println(s"You escaped with ${combat.player.inventory.getOrElse("Gold", 0)} gold")
      }
    }
  }

  private def optionallyHeal(player: Player) = {
    val potions = player.inventory.getOrElse("Potion of Healing", 0)
    if (potions > 0) {
      println(s"You have ${
        potions
      } Potion(s) of Healing. Would you like to heal?")
      println("[Y]es or [N]o")
      StdIn.readChar() match {
        case 'y' => {
          player.healCompletely()
          player.removeItem("Potion of Healing")
        }
        case _ =>
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val player = new Player()
    player.addItems("Potion of Healing", 1)
    gameLoop(player)
    println("Done")
  }
}
