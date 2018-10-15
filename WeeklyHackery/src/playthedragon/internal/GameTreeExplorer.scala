package playthedragon.internal

import playthedragon.internal.Action._
import playthedragon.internal.Fighter.{Dragon, Knight}

import scala.collection.mutable

object GameTreeExplorer {

  def findWinningActions(initialState: GameState): Option[List[Action]] = {

    val explorer = new GameTreeExplorer(initialState)

    while (explorer.toExplore.nonEmpty) {
      explorer.exploreNextState()
    }

    explorer.solution
  }

  def actionsToExplore(gameState: GameState): List[Action] = {
    if (gameState.dragonIsDead) {
      List.empty
    }
    else {
      ActionsBuilder()
        .withActionIf(Cure, gameState.dragonHealth < gameState.knightAttack)
        .withActionIf(Buff, gameState.dragonAttack < gameState.knightHealth && gameState.buffModifier > 0)
        .withActionIf(Debuff, gameState.knightAttack > 0 && gameState.debuffModifier > 0)
        .withActionIf(Attack, !gameState.knightIsDead)
        .toList
    }
  }

  private case class ActionsBuilder(actions: Set[Action] = Set()) {
    def withActionIf(action: Action, includeAction: Boolean): ActionsBuilder = {
      if (includeAction) this.copy(actions + action) else this
    }

    def toList: List[Action] =
      actions.toList
  }

  private def withActionIf(action: Action, predicate: GameState => Boolean): ((GameState, Set[Action])) => (GameState, Set[Action]) = {
    case (gameState: GameState, actions: Set[Action]) => {
      if (predicate(gameState)) (gameState, actions - action)
      else (gameState, actions)
    }
  }
}

class GameTreeExplorer(initialState: GameState) {

  import GameTreeExplorer._

  val explored: mutable.Set[GameState] = mutable.Set[GameState]()
  val toExplore: mutable.Queue[(GameState, List[Action])] = mutable.Queue[(GameState, List[Action])]()
  var solution: Option[List[Action]] = None

  private var state = initialState

  explored += initialState
  actionsToExplore(initialState).foreach(action => toExplore.enqueue((initialState, List(action))))

  def exploreNextState(): Unit = {
    toExplore.dequeue() match {
      case (gameState, actions) =>

        state =
          gameState
            .takeAction(Dragon, actions.head)
            .takeAction(Knight, Attack)

        if (state.knightIsDead) {
          toExplore.clear()
          solution = Some(actions.reverse)
        }
        else if (!explored.contains(state)) {
          explored += state
          actionsToExplore(state).foreach(action => toExplore.enqueue((state, action :: actions)))
        }
    }
  }
}

