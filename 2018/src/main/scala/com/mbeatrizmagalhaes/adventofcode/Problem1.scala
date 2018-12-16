package com.mbeatrizmagalhaes.adventofcode

class Problem1 extends Problems[Double, Double] {
  override def solve(lines: List[String]) = {
    val fullValues = lines.map(_.toDouble)
    val fullValuesArr = fullValues.toArray
    def findRepeatedFrequency(current: Double, foundFrequencies: Array[Double], values: Array[Double]): Double =
      if (foundFrequencies.contains(current)) current
      else findRepeatedFrequency(current + values.head, current +: foundFrequencies, if (values.tail.isEmpty) fullValuesArr else values.tail)
    
    (fullValues.sum, findRepeatedFrequency(0, Array[Double](), fullValuesArr))
  }

}
