package com.mbeatrizmagalhaes.adventofcode

class Problem1_2 extends Problem[Double] {
  override def solve(lines: List[String]): Double = {
    val fullValues = lines.map(_.toDouble)
    def findRepeatedFrequency(current: Double, foundFrequencies: List[Double], values: List[Double]): Double =
      if (foundFrequencies.contains(current)) current
      else findRepeatedFrequency(current + values.head, current :: foundFrequencies, if (values.tail.isEmpty) fullValues else values.tail)

    findRepeatedFrequency(0, List.empty, fullValues)
  }
}
