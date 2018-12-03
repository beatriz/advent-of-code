package com.mbeatrizmagalhaes.adventofcode

import scala.io.Source

object AoC2018 extends App {
  val problemN: String = args(0)

  val problem =
    Class.forName(s"com.mbeatrizmagalhaes.adventofcode.Problem$problemN")
      .newInstance().asInstanceOf[Problem[_]]

  val result = problem.solve(readInputLines)

  println(s"The result for problem $problemN is: $result")

  def readInputLines: List[String] =
    Source.fromFile(s"input/${problemN.take(1)}.txt").getLines.toList
}

trait Problem[T] {
  def solve(lines: List[String]): T
}
