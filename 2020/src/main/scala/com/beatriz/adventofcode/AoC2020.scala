package adventofcode

import scala.io.Source

object AoC2020 extends App {
  val problemN: String = args(0)
  val problem = Class.forName(s"adventofcode.Day$problemN")
  .newInstance().asInstanceOf[Problem[_, _]]

  val input: Seq[String] = Source.fromFile(s"input/day$problemN.txt").getLines.toSeq

  val (res1, res2) = problem.solve(input)

  println(s"Day $problemN Part 1: $res1")
  println(s"Day $problemN Part 2: $res2")
}

trait Problem[A, B] {
  def solve(lines: Seq[String]): (A, B)
}