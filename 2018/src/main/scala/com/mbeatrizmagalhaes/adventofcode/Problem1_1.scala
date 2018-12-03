package com.mbeatrizmagalhaes.adventofcode

class Problem1_1 extends Problem[Double] {
  override def solve(lines: List[String]) = lines.map(_.toDouble).sum
}
