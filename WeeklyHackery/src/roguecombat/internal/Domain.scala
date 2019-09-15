package roguecombat.internal

import roguecombat.internal.creatures.{Goblin, Human}

object Domain {
  case class Game(player: Human, goblin: Goblin)
}
