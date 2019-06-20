import mill._
import mill.scalalib._
import mill.scalalib

object WeeklyHackery extends ScalaModule {
  def scalaVersion = "2.12.8"

  //def mainClass = Some("labyrinthsareawesome.LabyrinthsAreAwesome")
  def mainClass = Some("sudoku.Sudoku")

//  def scalacOptions = Seq(
//    "-target:jvm-1.8",
//    "-encoding", "UTF-8",
//    "-unchecked",
//    "-deprecation",
//    "-Xfuture",
//    "-Yno-adapted-args",
//    "-Ywarn-dead-code",
//    "-Ywarn-numeric-widen",
//    "-Ywarn-value-discard",
//    "-Ywarn-unused"
//  )

  def ivyDeps = Agg(
    ivy"org.scala-lang.modules::scala-xml:1.1.1",
    ivy"org.apache.commons:commons-text:1.3",
    ivy"org.typelevel::cats-core:1.4.0",
    ivy"org.typelevel::cats-effect:1.2.0",
    ivy"co.fs2::fs2-core:1.0.2"
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
