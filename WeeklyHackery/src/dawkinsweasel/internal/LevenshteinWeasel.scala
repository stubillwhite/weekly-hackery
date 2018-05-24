package dawkinsweasel.internal

import org.apache.commons.text.similarity.LevenshteinDistance

import scala.util.Random

class LevenshteinWeasel extends DawkinsWeasel {
  private val levenshteinDistance: LevenshteinDistance = new LevenshteinDistance()

  override def fitness(individual: String): Int = {
    idealIndividual.length - levenshteinDistance.apply(individual, idealIndividual)
  }

  override def nextGeneration(population: List[String]): List[String] = {
    def mutateGenes(individual: String): String = {
      individual
        .map(_.toString)
        .map(gene => if (Random.nextInt(100) < 4) mutateGene(gene) else gene)
        .mkString
    }

    Iterator.continually(population.head)
      .take(population.length)
      .map(mutateGenes)
      .toList
  }

  private def mutateGene(gene: String): String = {
    Random.nextInt(3) match {
      case 0 => randomGene().toString
      case 1 => ""
      case 2 => randomGene() + gene
    }
  }
}
