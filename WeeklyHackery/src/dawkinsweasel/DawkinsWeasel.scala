package dawkinsweasel

import dawkinsweasel.internal.Evolver
import dawkinsweasel.internal.Evolver._

import scala.util.Random

class DawkinsWeasel extends Evolvable[String] {
  private val genes = "ABCDEFGHIJKLMNOPQRSTUVWXYZ "
  private val idealIndividual = "METHINKS IT IS A DAWKINS WEASEL"

  override def initialPopulation(): List[String] =
    Iterator.continually(randomIndividual())
      .take(100)
      .toList

  override def fitness(individual: String): Int =
    individual
      .toList
      .zip(idealIndividual.toList)
      .count { case (a, b) => a == b }

  override def continueEvolving(population: List[String], generation: Int): Boolean = {
    println(s"Fittest: ${population.head} (generation: ${generation}, fitness: ${fitness(population.head)})")

    (generation < 1000) && (population.head != idealIndividual)
  }

  override def nextGeneration(population: List[String]): List[String] = {
    def mutateGenes(individual: String): String = {
      individual.map(gene => if (Random.nextInt(100) < 5) randomGene() else gene).mkString
    }

    Iterator.continually(population.head)
      .take(population.length)
      .map(mutateGenes)
      .toList
  }

  private def randomIndividual(): String = {
    Iterator.continually(randomGene())
      .take(idealIndividual.length)
      .mkString
  }

  private def randomGene(): Char = {
    genes(Random.nextInt(genes.length))
  }
}

object DawkinsWeasel {
  def main(args: Array[String]): Unit = {
    val weasels = Evolver.evolve(new DawkinsWeasel)
    println(weasels.head)
  }
}
