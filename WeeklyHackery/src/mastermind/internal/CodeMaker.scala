package mastermind.internal

import mastermind.internal.Domain.{Code, CodePeg}
import mastermind.internal.Domain.Color._

import scala.util.Random

object CodeMaker {
  def apply(): CodeMaker =
    new CodeMaker()
}

class CodeMaker() {

  private val colors = List(Red, Blue, Yellow, Green, White, Black)

  def createCode(): Code = {
    Array.fill[CodePeg](4)(randomCodePeg)
  }

  private def randomCodePeg: CodePeg = {
    val randomColor = colors(Random.nextInt(colors.length))
    CodePeg(randomColor)
  }
}