package labyrinthsareawesome

import labyrinthsareawesome.internal.explorer.AlwaysTurnRightExplorer
import labyrinthsareawesome.internal.generator.RecursiveBacktracker

object LabyrinthsAreAwesome {

  def main(args: Array[String]): Unit = {
    val labyrinth = RecursiveBacktracker.generateLabyrinth(8, 8, displayProgress = true)
    AlwaysTurnRightExplorer.escapeLabyrinth(labyrinth, true)
  }
}
