package texasholdem

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import texasholdem.Deck.Card
import texasholdem.Deck.Suits._
import texasholdem.PokerHandClassifier.PokerHands._
import texasholdem.PokerHandClassifier.classifyHand
import texasholdem.testcommon.TestCards

import scala.util.Random.shuffle

class PokerHandClassifierTest extends FlatSpec with Matchers with MockitoSugar with TestCards {

  behavior of "classifyHand"

  it should "order hands of different type based on hand rank" in {
    // Given
    val cards = List(
      shuffledHand(CJ, CQ, CK, CK, CA),
      shuffledHand(C9, CT, CJ, CQ, CK),
      shuffledHand(C9, D9, H9, S9, C8),
      shuffledHand(C9, D9, H9, D8, C8),
      shuffledHand(C2, C3, C9, CQ, CJ),
      shuffledHand(C9, D8, C7, C6, C5),
      shuffledHand(C9, D9, H9, C2, C3),
      shuffledHand(C9, D9, C8, D8, C4),
      shuffledHand(C9, D9, C2, C3, C4),
      shuffledHand(C2, C4, D8, CJ, CA))

    // When
    val classifiedAndOrderedHands = cards.reverse.map(classifyHand).sorted

    // Then
    classifiedAndOrderedHands should be (List(
      RoyalFlush(Clubs),
      StraightFlush(Clubs, 13),
      FourOfAKind(9),
      FullHouse(9, 8),
      Flush(Clubs, List(12, 11, 9, 3, 2)),
      Straight(9),
      ThreeOfAKind(9, List(3, 2)),
      TwoPair(9, 8, List(4)),
      Pair(9, List(4, 3, 2)),
      HighCard(List(14, 11, 8, 4, 2))
    ))
  }

  it should "order straight flush draws based on high card" in {
    // Given
    val cards = List(
      shuffledHand(C9, CT, CJ, CQ, CK),
      shuffledHand(C8, C9, CT, CJ, CQ),
      shuffledHand(C7, C8, C9, CT, CJ))

    // When
    val classifiedAndOrderedHands = cards.reverse.map(classifyHand).sorted

    // Then
    classifiedAndOrderedHands should be (List(
      StraightFlush(Clubs, 13),
      StraightFlush(Clubs, 12),
      StraightFlush(Clubs, 11)))
  }

  it should "order four of a kind draws based on rank" in {
    // Given
    val cards = List(
      shuffledHand(C9, D9, H9, S9, C5),
      shuffledHand(C8, D8, H8, S8, C5),
      shuffledHand(C7, D7, H7, S7, C5))

    // When
    val classifiedAndOrderedHands = cards.reverse.map(classifyHand).sorted

    // Then
    classifiedAndOrderedHands should be (List(
      FourOfAKind(9),
      FourOfAKind(8),
      FourOfAKind(7)))
  }

  it should "order full house draws based on triplet rank then pair rank" in {
    // Given
    val cards = List(
      shuffledHand(C9, D9, H9, C8, D8),
      shuffledHand(C8, C8, C8, CA, DA),
      shuffledHand(C8, C8, C8, CK, DK))

    // When
    val classifiedAndOrderedHands = cards.reverse.map(classifyHand).sorted

    // Then
    classifiedAndOrderedHands should be (List(
      FullHouse(9, 8),
      FullHouse(8, 14),
      FullHouse(8, 13)))
  }

  it should "order flush draws based on card ranks" in {
    // Given
    val cards = List(
      shuffledHand(C9, C8, C7, C6, C4),
      shuffledHand(C9, C8, C7, C5, C4),
      shuffledHand(C9, C8, C7, C5, C3))

    // When
    val classifiedAndOrderedHands = cards.reverse.map(classifyHand).sorted

    // Then
    classifiedAndOrderedHands should be (List(
      Flush(Clubs, List(9, 8, 7, 6, 4)),
      Flush(Clubs, List(9, 8, 7, 5, 4)),
      Flush(Clubs, List(9, 8, 7, 5, 3))))
  }

  it should "order straight draws based on high card" in {
    val cards = List(
      shuffledHand(D9, C8, C7, C6, C5),
      shuffledHand(D8, C7, C6, C5, C4),
      shuffledHand(D7, C6, C5, C4, C3))

    // When
    val classifiedAndOrderedHands = cards.reverse.map(classifyHand).sorted

    // Then
    classifiedAndOrderedHands should be (List(
      Straight(9),
      Straight(8),
      Straight(7)))
  }

  it should "order three of a kind draws based on rank and kickers" in {
    val cards = List(
      shuffledHand(C9, D9, H9, C6, C5),
      shuffledHand(C8, D8, H8, C5, C4),
      shuffledHand(C8, D8, H8, C4, C3))

    // When
    val classifiedAndOrderedHands = cards.reverse.map(classifyHand).sorted

    // Then
    classifiedAndOrderedHands should be (List(
      ThreeOfAKind(9, List(6, 5)),
      ThreeOfAKind(8, List(5, 4)),
      ThreeOfAKind(8, List(4, 3))))
  }

  it should "order two pair draws based on highest pair rank then second highest then kicker" in {
    val cards = List(
      shuffledHand(C9, D9, H7, S7, C5),
      shuffledHand(C9, D9, H6, S6, C5),
      shuffledHand(C9, D9, H6, S6, C4))

    // When
    val classifiedAndOrderedHands = cards.reverse.map(classifyHand).sorted

    // Then
    classifiedAndOrderedHands should be (List(
      TwoPair(9, 7, List(5)),
      TwoPair(9, 6, List(5)),
      TwoPair(9, 6, List(4))))
  }

  it should "order pair draws based on pair rank then kickers" in {
    val cards = List(
      shuffledHand(C9, D9, C6, C5, C4),
      shuffledHand(C8, D8, C6, C5, C4),
      shuffledHand(C8, D8, C6, C5, C3))

    // When
    val classifiedAndOrderedHands = cards.reverse.map(classifyHand).sorted

    // Then
    classifiedAndOrderedHands should be (List(
      Pair(9, List(6, 5, 4)),
      Pair(8, List(6, 5, 4)),
      Pair(8, List(6, 5, 3))))
  }

  it should "order high card draws based on kickers" in {
    val cards = List(
      shuffledHand(C9, D7, C6, C5, C4),
      shuffledHand(C9, D7, C6, C5, C3))

    // When
    val classifiedAndOrderedHands = cards.reverse.map(classifyHand).sorted

    // Then
    classifiedAndOrderedHands should be (List(
      HighCard(List(9, 7, 6, 5, 4)),
      HighCard(List(9, 7, 6, 5, 3))))
  }

  it should "order hands differing on kickers using all kickers" in {
    val cards = List(
      shuffledHand(CA, DQ, CT, D8, C6),
      shuffledHand(CK, DQ, CT, D8, C6),
      shuffledHand(CK, DJ, CT, D8, C6),
      shuffledHand(CK, DJ, C9, D8, C6),
      shuffledHand(CK, DJ, C9, D7, C6),
      shuffledHand(CK, DJ, C9, D7, C5))

    // When
    val classifiedAndOrderedHands = cards.reverse.map(classifyHand).sorted

    // Then
    classifiedAndOrderedHands should be (List(
      HighCard(List(14, 12, 10, 8, 6)),
      HighCard(List(13, 12, 10, 8, 6)),
      HighCard(List(13, 11, 10, 8, 6)),
      HighCard(List(13, 11,  9, 8, 6)),
      HighCard(List(13, 11,  9, 7, 6)),
      HighCard(List(13, 11,  9, 7, 5))))
  }

  private def shuffledHand(cards: Card*): List[Card] = {
    shuffle(cards).toList
  }
}
