organization := "no.lau.vdvil"

name := "play-gui"

version := "0.15-SNAPSHOT"

scalaVersion := "2.10.3"

externalPom()

//libraryDependencies += "org.scala-lang" % "scala-swing" % "2.9.1"

//libraryDependencies += "no.lau.vdvil" % "binding-layer" % "0.11-SNAPSHOT"

//libraryDependencies += "com.novocode" % "junit-interface" % "0.7" % "test->default"

//libraryDependencies += "junit" % "junit" % "4.8.1"


resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"