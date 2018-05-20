package texasholdem.internal

import texasholdem.internal.Deck.{Card, Rank, Suit}

import scala.annotation.tailrec

object PokerHandClassifier {

  /**
    * A poker hand.
    */
  sealed trait PokerHand extends Ordered[PokerHand] {
    /**
      * The rank of the hand relative to other PokerHands, with a higher value indicating a stronger hand.
      */
    val handRank: Int

    /**
      * The ranks of the cards which play a part in tie-breaking when comparing this PokerHand to another PokerHand with the same handRank.
      */
    val cardRanks: List[Rank]

    override def compare(that: PokerHand): Int = {
      that.handRank.compareTo(this.handRank) match {
        case 0 =>
          compareCardRanks(that.cardRanks, this.cardRanks)
        case x =>
          x
      }
    }

    @tailrec
    private def compareCardRanks(a: List[Rank], b: List[Rank]): Int = {
      if (a.isEmpty) {
        0
      } else {
        a.head.compareTo(b.head) match {
          case 0 => compareCardRanks(a.tail, b.tail)
          case x => x
        }
      }
    }
  }

  abstract class PokerHandBase(val handRank: Int) extends PokerHand {
  }

  object PokerHands {

    case class RoyalFlush(suit: Suit) extends PokerHandBase(10) {
      override val cardRanks: List[Rank] = Nil
    }

    case class StraightFlush(suit: Suit, highCard: Int) extends PokerHandBase(9) {
      override val cardRanks: List[Rank] = highCard :: Nil
    }

    case class FourOfAKind(rank: Rank) extends PokerHandBase(8) {
      override val cardRanks: List[Rank] = rank :: Nil
    }

    case class FullHouse(threeOfAKindRank: Rank, pairRank: Rank) extends PokerHandBase(7) {
      override val cardRanks: List[Rank] = threeOfAKindRank :: pairRank :: Nil
    }

    case class Flush(suit: Suit, kickers: List[Rank]) extends PokerHandBase(6) {
      override val cardRanks: List[Rank] = kickers
    }

    case class Straight(highCard: Rank) extends PokerHandBase(5) {
      override val cardRanks: List[Rank] = highCard :: Nil
    }

    case class ThreeOfAKind(rank: Rank, kickers: List[Rank]) extends PokerHandBase(4) {
      override val cardRanks: List[Rank] = rank :: kickers
    }

    case class TwoPair(highRank: Rank, lowRank: Rank, kickers: List[Rank]) extends PokerHandBase(3) {
      override val cardRanks: List[Rank] = highRank :: lowRank :: kickers
    }

    case class Pair(rank: Rank, kickers: List[Rank]) extends PokerHandBase(2) {
      override val cardRanks: List[Rank] = rank :: kickers
    }

    case class HighCard(kickers: List[Rank]) extends PokerHandBase(1) {
      override val cardRanks: List[Rank] = kickers
    }
  }

  import PokerHands._

  /**
    * A function that attempts to match cards to identify a poker hand, returning either the
    * cards unchanged if matching failed, or a classified poker hand if the cards were matched.
    */
  type PokerHandMatcher = (List[Card]) => Either[List[Card], PokerHand]

  private val rankBasedHandMatcher: PokerHandMatcher = (cards: List[Card]) => {
    val groupedByRank = cards
      .groupBy(_.rank)
      .values
      .toList
      .sortBy(x => (x.size, x.head.rank)).reverse

    groupedByRank match {
      case List(a, _, _, _) :: _ => Right(FourOfAKind(a.rank))
      case List(a, _, _) :: List(b, _) :: Nil => Right(FullHouse(a.rank, b.rank))
      case List(Card(highRank, _)) :: _ :: _ :: _ :: List(Card(lowRank, _)) :: Nil if (highRank - lowRank == 4) => Right(Straight(highRank))
      case List(a, _, _) :: kickers => Right(ThreeOfAKind(a.rank, kickers.flatten.map(_.rank)))
      case List(a, _) :: List(b, _) :: kickers => Right(TwoPair(a.rank, b.rank, kickers.flatten.map(_.rank)))
      case List(a, _) :: kickers => Right(Pair(a.rank, kickers.flatten.map(_.rank)))
      case _ => Left(cards)
    }
  }

  private val suitBasedHandMatcher: PokerHandMatcher = (cards: List[Card]) => {
    val groupedBySuit = cards
      .groupBy(_.suit)
      .values
      .toList
      .sortBy(_.size).reverse
      .map(x => x.sortBy(_.rank).reverse)

    groupedBySuit match {
      case List(Card(14, suit), _, _, _, _) :: Nil => Right(RoyalFlush(suit))
      case List(Card(highRank, suit), _, _, _, Card(lowRank, _)) :: Nil if (highRank - lowRank == 4) => Right(StraightFlush(suit, highRank))
      case List(Card(_, suit), _, _, _, _) :: Nil => Right(Flush(suit, cards.map(_.rank).sorted.reverse))
      case _ => Left(cards)
    }
  }

  private val highCardHandMatcher: PokerHandMatcher = (cards: List[Card]) => {
    Right(HighCard(cards.map(_.rank).sorted.reverse))
  }

  def classifyHand(cards: List[Card]): PokerHand = {
    val pokerHand = suitBasedHandMatcher(cards)
      .left.flatMap(rankBasedHandMatcher)
      .left.flatMap(highCardHandMatcher)

    pokerHand.right.get
  }
}
