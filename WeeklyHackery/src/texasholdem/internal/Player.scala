package texasholdem.internal

import texasholdem.internal.Deck.Card
import texasholdem.internal.PokerHandClassifier.PokerHand

case class Player(id: String,
                  holeCards: List[Card] = List(),
                  bestHand: Option[PokerHand] = None)
