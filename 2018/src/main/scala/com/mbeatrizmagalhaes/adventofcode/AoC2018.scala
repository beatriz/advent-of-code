package com.mbeatrizmagalhaes.adventofcode

import scala.io.Source
import scala.util.Try

object AoC2018 extends App {
  val problemN: String = args(0)
  val isTest = Try(args(1))
  val problem =
    Class.forName(s"com.mbeatrizmagalhaes.adventofcode.Problem$problemN")
      .newInstance().asInstanceOf[Problem[_]]

  val input: List[String] = {
    val suffix = Try(args(1)).toOption.map(_ => "_test").getOrElse("")
    Source.fromFile(s"input/${problemN.take(1)}$suffix.txt").getLines.toList
  }

  val result = problem.solve(input)

  println(s"The result for problem $problemN is: $result")

}

trait Problem[T] {
  def solve(lines: List[String]): T
}
