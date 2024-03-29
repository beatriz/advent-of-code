val scala3Version = "3.2.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "advent-of-code-2022",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1",
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )
