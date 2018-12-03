package com.mbeatrizmagalhaes.adventofcode

class Problem_11 extends Problem[Double] {
  override def solve(lines: List[String]) = lines.map(_.toDouble).sum
}
