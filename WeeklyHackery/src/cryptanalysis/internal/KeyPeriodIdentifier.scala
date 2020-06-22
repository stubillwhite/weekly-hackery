package cryptanalysis.internal

object KeyPeriodIdentifier {
  def apply(language: Language, maxKeyLength: Int): KeyPeriodIdentifier =
    new KeyPeriodIdentifier(language, maxKeyLength)
}

class KeyPeriodIdentifier(language: Language, maxKeyPeriod: Int) {

  case class PossibleKeyPeriod(period: Int, indexOfCoincidence: Double)

  def probableKeyPeriod(text: String): Int = {
    val possiblePeriods = findPossibleKeyPeriods(text)

    def op(acc: Seq[(PossibleKeyPeriod, Double)], x: PossibleKeyPeriod): Seq[(PossibleKeyPeriod, Double)] = {
      acc ++ Seq((x, x.indexOfCoincidence - acc.maxBy(_._2)._1.indexOfCoincidence))
    }

    val zero = Seq[(PossibleKeyPeriod, Double)]((possiblePeriods.head, 0.0))

    val periodsAndDelta = possiblePeriods.tail.foldLeft(zero)(op)

//    println("Probable key lengths")
//    println("  Length   IoC    Delta")
//    periodsAndDelta.foreach { case (k, d) => println(f"    ${k.period}%-2s     ${k.indexOfCoincidence}%1.2f   ${d}%-1.2f")}

    periodsAndDelta.maxBy(_._2)._1.period
  }

  def findPossibleKeyPeriods(text: String): Seq[PossibleKeyPeriod] = {
    for {
      period <- 1 to maxKeyPeriod
    } yield {
      val texts = createTextsForPeriod(text, period)
      val iocsForTexts = texts.map(indexOfCoincidence)
      val meanIOC = iocsForTexts.sum / iocsForTexts.length
      PossibleKeyPeriod(period, meanIOC)
    }
  }

  private def indexOfCoincidence(text: String): Double = {
    val distribution = FrequencyDistribution(language, text)
    val total = distribution.Frequencies.values.sum

    def calcMetric(ch: Char): Double = {
      val n = distribution.Frequencies(ch)
      (n * (n - 1)).toDouble / (total * (total - 1)) * language.Letters.length
    }

    language.Letters
      .map(calcMetric)
      .sum
  }

  private[internal] def createTextsForPeriod(text: String, step: Int): Seq[String] = {
    for {
      n <- 0 until step
    } yield {
      text
        .grouped(step)
        .toList
        .map(s => s.lift(n).getOrElse(""))
        .mkString
    }
  }
}
