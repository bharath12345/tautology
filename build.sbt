name := "tautology"

organization := "in.bharathwrites"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-language:_",
  "-target:jvm-1.7",
  "-encoding", "UTF-8"
)