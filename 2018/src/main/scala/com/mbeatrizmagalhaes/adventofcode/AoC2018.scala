package com.mbeatrizmagalhaes.adventofcode

import scala.io.Source
import scala.util.Try

object AoC2018 extends App {
  val problemN: String = args(0)
  val isTest = Try(args(1))
  val problem =
    Class.forName(s"com.mbeatrizmagalhaes.adventofcode.Problem$problemN")
      .newInstance().asInstanceOf[Problems[_,_]]

  val input: List[String] = {
    val suffix = Try(args(1)).toOption.map(_ => "_test").getOrElse("")
    Source.fromFile(s"input/$problemN$suffix.txt").getLines.toList
  }

  val (result1, result2) = problem.solve(input)

  println(s"The result for part 1 of day $problemN is: $result1")
  println(s"The result for part 2 of day $problemN is: $result2")
}

trait Problems[A, B] {
  def solve(lines: List[String]): (A, B)
}
