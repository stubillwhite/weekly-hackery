package playthedragon

import playthedragon.internal.Action._
import playthedragon.internal.Fighter._
import playthedragon.internal.{GameState, GameTreeExplorer}

import scala.io.Source

object PlayTheDragon {

  private val smallInput = parseProblemInput("C-small-practice.tsv")

  def main(args: Array[String]): Unit = {

    val input = smallInput

    input.foreach(displaySolution)
    println("Done")
  }

  private def parseProblemInput(filename: String): Seq[GameState] = {
    val inputStream = getClass.getResourceAsStream(filename)
    val lines = Source.fromInputStream(inputStream).getLines.toList

    def createGameState(problemInput: List[Long]): GameState = {
      val hd :: ad :: hk :: ak :: b :: d :: Nil = problemInput
      GameState(hd, hd, ad, hk, ak, b, d)
    }

    lines
      .drop(1)
      .map(_.split("\\s+"))
      .map(_.map(_.toLong).toList)
      .map(createGameState)
  }

  private def displaySolution(initialState: GameState): Unit = {
    val solution = GameTreeExplorer.findWinningActions(initialState)

    solution match {
      case Some(actions) =>
        println(s"Winnable ${actions.length}")
        actions.foldLeft(initialState)(takeActionAndDisplayState)

      case None =>
        println("Unwinnable")
    }

    println
  }

  private def takeActionAndDisplayState(gameState: GameState, action: Action): GameState = {
    val newState = gameState.takeAction(Dragon, action).takeAction(Knight, Attack)
    println(s"    ${action.formatted("%-10s")} ${gameState.formatted("%-30s")} => ${newState.formatted("%-30s")}")
    newState
  }
}
