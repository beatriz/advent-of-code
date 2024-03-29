ThisBuild / scalaVersion := "2.13.3"
ThisBuild / organization := "com.beatriz"
ThisBuild / version := "0.1.0"

lazy val root = (project in file("."))
  .settings(
    name := "advent-of-code-2020",
    scalafmtOnCompile := true,
    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
  )
