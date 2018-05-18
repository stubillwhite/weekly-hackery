package texasholdem

import texasholdem.Deck.Card
import texasholdem.PokerHandClassifier.PokerHand

case class Player(id: String,
                  holeCards: List[Card] = List(),
                  bestHand: Option[PokerHand] = None)
