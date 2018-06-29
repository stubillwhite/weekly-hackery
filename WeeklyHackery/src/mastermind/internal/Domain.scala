package mastermind.internal

object Domain {

  type Code = Array[CodePeg]
  def Code(codePegs: CodePeg*): Code = Array(codePegs: _*)

  type Feedback = Array[KeyPeg]
  def Feedback(keyPegs: KeyPeg*): Feedback = Array(keyPegs: _*)

  object Color extends Enumeration {
    type Color = Value
    val Red, Blue, Yellow, Green, White, Black = Value
  }

  case class CodePeg(color: Color.Color)

  object CodePegs {
    val Red = CodePeg(Color.Red)
    val Blue = CodePeg(Color.Blue)
    val Yellow = CodePeg(Color.Yellow)
    val Green = CodePeg(Color.Green)
    val White = CodePeg(Color.White)
    val Black = CodePeg(Color.Black)
  }

  case class KeyPeg(color: Color.Color)

  object KeyPegs {
    val FullyCorrect = KeyPeg(Color.Black)
    val PartiallyCorrect = KeyPeg(Color.White)
  }

  case class Round(guess: Code, feedback: Feedback)
}
