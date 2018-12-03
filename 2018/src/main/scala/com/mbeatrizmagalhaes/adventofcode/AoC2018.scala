package com.mbeatrizmagalhaes.adventofcode

import scala.io.Source

object AoC2018 extends App {
  val problemN: String = "22"

  val problem: Problem = Class.forName(s"com.mbeatrizmagalhaes.adventofcode.Problem_$problemN").newInstance().asInstanceOf[Problem]

  val result = problem.solve(readInputLines)
  println(s"The result for problem $problemN is: $result")

  def readInputLines: List[String] =
    Source.fromFile("/Users/bmagalhaes/code/advent-of-code/input.txt").getLines.toList
}

trait Problem {
  def solve(lines: List[String]): String
}
