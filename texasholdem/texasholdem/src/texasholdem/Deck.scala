package texasholdem

import texasholdem.Deck.Card
import texasholdem.Deck.Suits.{Clubs, Diamonds, Hearts, Spades}

import scala.util.Random

object Deck {

  type Rank = Int

  sealed trait Suit
  
  object Suits {
    case object Clubs extends Suit
    case object Diamonds extends Suit
    case object Hearts extends Suit
    case object Spades extends Suit
  }
  
  case class Card(rank: Rank, suit: Suit)

  def apply(): Deck = {
    val cards = for {suit <- List(Clubs, Diamonds, Hearts, Spades)
                     rank <- 2 to 14} yield Card(rank, suit)
    new Deck(cards)
  }
}

class Deck(val cards: List[Card]) {
  def shuffle(): Deck = new Deck(Random.shuffle(cards))

  def deal(n: Int): (Deck, List[Card]) = (new Deck(cards.drop(n)), cards.take(n))
}
