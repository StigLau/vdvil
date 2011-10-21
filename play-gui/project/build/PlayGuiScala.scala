import sbt._

class PlayGuiProject(info: ProjectInfo) extends DefaultProject(info)
{
  //  override def managedStyle = ManagedStyle.Maven
  val mavenLocal = "Local Maven Repository" at "file://" + Path.userHome + "/.m2/repository"


  override def testFrameworks = super.testFrameworks ++ List(new TestFramework("com.novocode.junit.JUnitFrameworkNoMarker"))

  //val scalatest = "org.scalatest" % "scalatest" % "1.2"
  val scala_swing = "org.scala-lang" % "scala-swing" % "2.9.1"
  val binding_layer = "no.lau.vdvil" % "binding-layer" % "0.11-SNAPSHOT"
  //For running JUnit tests
   val junitInterface = "com.novocode" % "junit-interface" % "0.7" % "test->default"

  val junit = "junit" % "junit" % "4.8.1"
}
