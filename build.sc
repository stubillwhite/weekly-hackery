import mill._
import mill.scalalib._
import mill.scalalib

object WeeklyHackery extends ScalaModule {
  def scalaVersion = "2.11.7"

  //def mainClass = Some("labyrinthsareawesome.LabyrinthsAreAwesome")
  def mainClass = Some("gameofblobs.GameOfBlobs")

  def ivyDeps = Agg(
    ivy"org.apache.commons:commons-text:1.3"
  )

  object test extends Tests{
    def forkWorkingDir = ammonite.ops.pwd
    def ivyDeps = Agg(
      ivy"org.objenesis:objenesis:2.1",
      ivy"org.hamcrest:hamcrest-core:1.3",
      ivy"org.mockito:mockito-core:1.10.19",
      ivy"org.scalatest::scalatest:3.0.0"
    )
    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}
