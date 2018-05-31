package markvshaney.internal

import markvshaney.internal.MarkovTextGenerator._

import scala.annotation.tailrec
import scala.util.Random

object MarkovTextGenerator {

  type TextStream = Stream[String]

  type State = List[String]
  type Event = String
  case class Transition(event: Event, toState: State)
  type WeightedTransitions = Map[Transition, Int]
  type MarkovModel = Map[State, WeightedTransitions]

  def apply(textStream: TextStream,
            n: Int = 2,
            randomNumberProvider: RandomNumberProvider = new DefaultRandomNumberProvider()): MarkovTextGenerator = {

    val emptyMap = Map[State, Map[Transition, Int]]()

    val values = textStream
      .sliding(n + 1, 1)
      .map(_.toList)

    val stateTransitions = values.foldLeft(emptyMap)(addTransition)

    new MarkovTextGenerator(stateTransitions, randomNumberProvider)
  }

  private def addTransition(acc: MarkovModel, tokens: List[String]): MarkovModel = {
    val fromState = tokens.dropRight(1)
    val event = tokens.last
    val toState = tokens.drop(1)

    val transitionsForState: Map[Transition, Int] = acc.getOrElse(fromState, Map())
    val transition = Transition(event, toState)
    val frequency = transitionsForState.getOrElse(transition, 0)

    val newTransitionsForState = transitionsForState.updated(transition, frequency + 1)

    acc.updated(fromState, newTransitionsForState)
  }

  trait RandomNumberProvider {
    def nextInt(n: Int): Int
  }

  class DefaultRandomNumberProvider extends RandomNumberProvider {
    override def nextInt(n: Int): Int = Random.nextInt(n)
  }
}

class MarkovTextGenerator(model: MarkovModel,
                          randomNumberProvider: RandomNumberProvider) {

  def generateText(state: State = randomState()): Stream[Event] = {
    val initialTransition = randomTransitionFrom(model(state))

    val transitions = Stream.iterate(initialTransition)(nextTransition).takeWhile { _ != null }

    state.toStream ++ transitions.map(_.event)
  }

  private def randomState(): State = {
    randomElementFrom(model.keySet.toList)
  }

  def nextTransition(previousTransition: Transition): Transition = {
    val currentState = previousTransition.toState

    model.get(currentState) match {
      case Some(transitions) => randomTransitionFrom(transitions)
      case None => null
    }
  }

  private def randomTransitionFrom(weightedTransitions: WeightedTransitions): Transition = {
    val rnd = randomNumberProvider.nextInt(weightedTransitions.values.sum) + 1

    @tailrec
    def randomTransitionFromIter(runningWeight: Int, remainingWeightedTransitions: WeightedTransitions): Transition = {
      val (transition, weight) = remainingWeightedTransitions.head

      if (rnd <= runningWeight + weight) {
        transition
      } else {
        randomTransitionFromIter(runningWeight + weight, remainingWeightedTransitions.tail)
      }
    }

    randomTransitionFromIter(0, weightedTransitions)
  }

  private def randomElementFrom[A](items: List[A]): A = {
    items(randomNumberProvider.nextInt(items.length))
  }
}