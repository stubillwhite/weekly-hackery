# Texas Hold 'em

To edit the code:

- brew install mill
- ./mill-intellij.sh
- idea .

## General thoughts

- Using Mill for building in the hopes of getting off SBT. It seems pretty nice. No IntelliJ plugin, though, so I have to exit and re-run the `mill-intellij.sh` script every time I change dependencies to regenerate the classpath.
- I've tried to use standard terms to reflect the domain, but unforunately "hand" has multiple meanings (hand of a game, player hand), as does "deal" (hand of a game, removing a card from the deck). So I've invented PlayerHand (the hand of cards a player has) and Round (a hand of the game).
- I'm using lists to hold cards, and empty lists to indicate that cards aren't present. That's a bit Lispy. It feels like perhaps I should be using Optional to more tightly define the data model, but then that makes the actual dealing clunkier -- its easier to append cards to an empty list than to an empty list in an Optional.

## Stuff I'm happy with

- It's immutable
- The game structure seems clear from the code

## Stuff I'm less happy with

- Scala enums feel clunky. I followed the pattern from https://stackoverflow.com/a/25923651, but is there a better way?
- The way I've handled updates (folding a game state over incoming events, which in this case are cards) is how I'd do this in other languages, but I'm just doing that out of force of habit. Are there alternative approaches?
- ScalaTest FlatSpec always feels backwards to me, and I'm interested if there's better specs (or I should just suck it up). I'd like to use given/when/then for test naming ("behaviour of GIVEN, when WHEN, then THEN") which I think is a well-accepted approach across a few languages, but FlatSpec forces naming of "behaviour of WHEN, it should THEN when GIVEN", which just feels unintuitive. Not a biggie, just an irritation.
- The definition of Rank feels off to me. Ideally I'd want it to be constrained to the valid set of values but couldn't see an easy way to do that without resorting to enums.
- Updating nested case classes feels long-winded and boilerlate-y (see Round.dealToPlayers). I should probably look into lenses, maybe? Does that actually reduce boilerplate in this case? Is there a better way to do this?
- Chaining methods feels clunky; is there a way to do this without composing the functions first and then applying? 
