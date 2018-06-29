package mastermind.internal

import mastermind.internal.Domain.CodePegs._
import mastermind.internal.Domain.KeyPegs._
import mastermind.internal.Domain.{Code, Feedback}
import mastermind.internal.FeedbackCalculator._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class FeedbackCalculatorTest extends FlatSpec with Matchers with MockitoSugar {

  behavior of "calculateFeedback"

  it should "give feedback for completely incorrect pins" in {
    calculateFeedback(Code(Red, White), Code(Yellow, Blue)) should be(Feedback())
  }

  it should "give feedback for single partially correct pin" in {
    calculateFeedback(Code(Red, White), Code(Yellow, Red)) should be(Feedback(PartiallyCorrect))
  }

  it should "give feedback for multiple partially correct pins" in {
    calculateFeedback(Code(Red, White), Code(White, Red)) should be(Feedback(PartiallyCorrect, PartiallyCorrect))
  }

  it should "give feedback for single fully correct pin" in {
    calculateFeedback(Code(Red, White), Code(Red, Blue)) should be(Feedback(FullyCorrect))
  }

  it should "give feedback for multiple fully correct pins" in {
    calculateFeedback(Code(Red, White), Code(Red, White)) should be(Feedback(FullyCorrect, FullyCorrect))
  }

  it should "give feedback for fully and partially correct pins" in {
    calculateFeedback(Code(Red, White, Blue), Code(Red, Black, White)) should be(Feedback(FullyCorrect, PartiallyCorrect))
  }

  it should "give feedback for duplicate pins" in {
    calculateFeedback(Code(Red, Red, Blue, Blue), Code(Red, Red, Red, Blue)) should be(Feedback(FullyCorrect, FullyCorrect, FullyCorrect))
  }
}
