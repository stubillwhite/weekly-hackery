package texasholdem

import texasholdem.Deck.Card

case class CommunityCards(flop: List[Card] = List(),
                          turn: List[Card] = List(),
                          river: List[Card] = List())

