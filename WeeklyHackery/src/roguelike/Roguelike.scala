package roguelike

import roguelike.internal.creatures
import roguelike.internal.creatures.{Combat, Goblin, Player, resolveCombat}

import scala.util.Random

object Roguelike {

  def lootCorpse(player: Player): Unit = {
    val gold = Random.nextInt(5)
    println(s"Found ${gold} gold!")
    player.addItems("Gold", gold)
  }

  def main(args: Array[String]): Unit = {
    val combat = Combat(new Player(), new Goblin())
    resolveCombat(combat)
    lootCorpse(combat.player)

    println(combat.player)
    println("Done")
  }
}
