package texasholdem

import texasholdem.Deck.{Card, Rank, Suits}
import texasholdem.PokerHandClassifier.PokerHand
import texasholdem.PokerHandClassifier.PokerHands._

object Display {

  import Suits._

  def displayState(round: Round): Unit = {
    val communityCards = round.communityCards.map(cardToString)

    println(
      s"""
         |Community cards:
         |  Flop:  ${communityCards.take(3).mkString(" ")}
         |  Turn:  ${communityCards(3)}
         |  River: ${communityCards(4)}
         |
         |Player hole cards:""".stripMargin)

    round.playOrder.foreach(playerId => {
      println(s"""  ${playerId}: ${round.playersById(playerId).holeCards.map(cardToString).mkString(" ")}""")
    })

    println

    val playerBestHands = for {playerId <- round.playOrder
                               bestHand <- round.playersById(playerId).bestHand} yield (bestHand, playerId)

    val finalStandings = playerBestHands.sortBy(_._1)

    finalStandings.foreach(x => {
      println(s"${x._2}: ${pokerHandToString(x._1)}")
    })
  }

  private def rankToString(rank: Rank): String = {
    "2,3,4,5,6,7,8,9,10,J,Q,K,A".split(',')(rank - 2)
  }

  private def cardToString(card: Card): String = {
    val suitSymbol = card.suit match {
      case Clubs => "\u2663"
      case Diamonds => "\u2666"
      case Hearts => "\u2665"
      case Spades => "\u2664"
    }

    rankToString(card.rank) + suitSymbol
  }

  private def pokerHandToString(pokerHand: PokerHand): String = {

    pokerHand match {
      case RoyalFlush(suit) => s"Royal flush (${suit})"
      case StraightFlush(suit, highCard) => s"Straight flush, ${suit}, ${rankToString(highCard)} high"
      case FourOfAKind(rank) => s"Four of kind, ${rankToString(rank)}s"
      case FullHouse(tripleRank, pairRank) => s"Full house (${rankToString(tripleRank)}s over ${rankToString(pairRank)}s)"
      case Flush(suit, kickers) => s"${suit} flush (${kickers.map(rankToString).mkString(", ")} kickers)"
      case Straight(highCard) => s"Straight (${rankToString(highCard)} high)"
      case ThreeOfAKind(rank, _) => s"Three of a kind (${rankToString(rank)}s)"
      case TwoPair(highRank, lowRank, _) => s"Two pair (${rankToString(highRank)}s and ${rankToString(lowRank)}s)"
      case Pair(rank, kickers) => s"Pair of ${rankToString(rank)}s (${kickers.map(rankToString).mkString(", ")} kickers)"
      case HighCard(kickers) => s"High card (${kickers.map(rankToString).mkString(", ")} kickers)"
    }
  }
}
