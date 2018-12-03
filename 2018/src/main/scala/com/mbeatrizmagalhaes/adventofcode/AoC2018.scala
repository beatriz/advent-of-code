package com.mbeatrizmagalhaes.adventofcode

import scala.io.Source

object AoC2018 extends App {
  val problemN: String = "22"

  val problem: Problem[_] = Class.forName(s"com.mbeatrizmagalhaes.adventofcode.Problem_$problemN").newInstance().asInstanceOf[Problem[_]]

  val result = problem.solve(readInputLines)
  println(s"The result for problem $problemN is: $result")

  def readInputLines: List[String] =
    Source.fromFile("input.txt").getLines.toList
}

trait Problem[T] {
  def solve(lines: List[String]): T
}
