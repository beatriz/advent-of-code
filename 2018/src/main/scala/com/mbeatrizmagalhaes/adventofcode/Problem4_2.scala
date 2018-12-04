package com.mbeatrizmagalhaes.adventofcode

class Problem4_2 extends Problem[Int] {
  override def solve(lines: List[String]) = {
    val mapWithMinutes = Problem4_1.mapWithMinutes(lines)
    val maxGuardWithMinutes = mapWithMinutes.filter(_._2.nonEmpty).mapValues(_.groupBy(identity).mapValues(_.size).maxBy(_._2)).maxBy(_._2._2)
    val maxId = maxGuardWithMinutes._1
    val maxMinute = maxGuardWithMinutes._2._1
    maxId.toInt * maxMinute
  }
}


