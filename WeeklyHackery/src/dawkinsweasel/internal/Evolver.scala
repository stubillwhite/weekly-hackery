package dawkinsweasel.internal

import scala.collection.immutable.Stream.iterate

object Evolver {

  /**
    * A population of individuals which can be evolved.
    *
    * @tparam A the type of the individuals.
    */
  trait Evolvable[A] {
    /**
      * Create the initial population.
      *
      * @return the initial population
      */
    def initialPopulation(): List[A]

    /**
      * Calculate the fitness of a particular individual, where a higher number indicates a fitter individual.
      *
      * @param individual the individual
      * @return the fitness
      */
    def fitness(individual: A): Int

    /**
      * Check whether to continue to the next generation
      *
      * @param population the population of the current generation, sorted in descending fitness order
      * @param generation the current generation number
      * @return true if another round of evolution should be performed, false otherwise
      */
    def continueEvolving(population: List[A], generation: Int): Boolean

    /**
      * Produce the next generation by reproducing and mutating the current population.
      *
      * @param population The population of the current generation, sorted in descending fitness order
      * @return the newly mutated population
      */
    def nextGeneration(population: List[A]): List[A]
  }

  def evolve[A](evolvable: Evolvable[A]): List[A] = {
    val initialPopulation = evolvable.initialPopulation()
    val nextGeneration = (x: List[A]) => evolvable.nextGeneration(x.sortBy(evolvable.fitness).reverse)

    val populations = iterate(initialPopulation)(nextGeneration)

    populations.zipWithIndex
      .dropWhile { case (population, generation) => evolvable.continueEvolving(population, generation) }
      .head
      ._1
  }
}
