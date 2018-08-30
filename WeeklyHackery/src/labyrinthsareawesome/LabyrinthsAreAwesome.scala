package labyrinthsareawesome

import labyrinthsareawesome.internal.display.LabyrinthRenderer
import labyrinthsareawesome.internal.generator.RecursiveBacktracker

object LabyrinthsAreAwesome {

  def main(args: Array[String]): Unit = {
    val labyrinth = RecursiveBacktracker.generateLabyrinth(20, 10, displayProgress = true)
    val characterDisplay = LabyrinthRenderer.render(labyrinth)

    println(characterDisplay.render())
    println("Done")
  }
}
