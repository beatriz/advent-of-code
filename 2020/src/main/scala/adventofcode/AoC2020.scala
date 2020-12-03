package adventofcode

import scala.io.Source
import scala.util.Try

object AoC2020 extends App {
  val problemN: String = args(0)
  val problem = Class.forName(s"adventofcode.Day$problemN")
    .newInstance().asInstanceOf[Problem[_, _]]
  val isTest = Try(args(1)).toOption.isDefined

  val input: Seq[String] = {
    val fileName = if(isTest) "test" else "day" + problemN
    Source.fromFile(s"input/$fileName.txt").getLines.toSeq
  }

  val (res1, res2) = problem.solve(input)

  println(s"Day $problemN Part 1: $res1")
  println(s"Day $problemN Part 2: $res2")
}

trait Problem[A, B] {
  def solve(lines: Seq[String]): (A, B)
}
