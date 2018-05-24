package dawkinsweasel

import dawkinsweasel.internal.{DawkinsWeasel, Evolver, LevenshteinWeasel}

object DawkinsWeasel {
  def main(args: Array[String]): Unit = {
    println("Dawkins weasel")
    val dawkinsWeasels = Evolver.evolve(new DawkinsWeasel)
    println(dawkinsWeasels.head)

    println

    println("Levenshtein weasel")
    val levenshteinWeasels = Evolver.evolve(new LevenshteinWeasel)
    println(levenshteinWeasels.head)
  }
}
