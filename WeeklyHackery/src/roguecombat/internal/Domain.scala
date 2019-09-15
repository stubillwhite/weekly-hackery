package roguecombat.internal

import roguecombat.internal.attributes._
import roguecombat.internal.creatures.Human

object Domain {
  case class Game(player: Human, monster: Monster)
}
