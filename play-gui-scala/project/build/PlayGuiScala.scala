import sbt._

class PlayGuiProject(info: ProjectInfo) extends DefaultProject(info)
{
  //  override def managedStyle = ManagedStyle.Maven
  val mavenLocal = "Local Maven Repository" at "file://" + Path.userHome + "/.m2/repository"

  //For running JUnit tests
  val junitInterface = "com.novocode" %% "junit-interface" % "0.4.0" from "http://github.com/downloads/bryanjswift/junit-interface/junit-interface-0.4.0.jar"

  override def testFrameworks = super.testFrameworks ++ List(new TestFramework("com.novocode.junit.JUnitFrameworkNoMarker"))

  val scalatest = "org.scalatest" % "scalatest" % "1.2"
  val scala_swing = "org.scala-lang" % "scala-swing" % "2.8.0"
  val tagger_gui = "no.lau.vdvil" % "tagger-gui-scala" % "0.6-SNAPSHOT"


  val junit = "junit" % "junit" % "4.8.1"
}
