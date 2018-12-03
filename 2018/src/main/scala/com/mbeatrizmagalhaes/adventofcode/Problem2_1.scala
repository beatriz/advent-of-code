package com.mbeatrizmagalhaes.adventofcode

class Problem2_1 extends Problem[Int] {
  implicit def bool2int(b:Boolean): Int = if (b) 1 else 0

  override def solve(lines: List[String]): Int = {
    val (x1, x2) = lines.foldLeft((0, 0)){
      case ((acc1, acc2), str) =>
        val x = str.distinct.map(c => str.count(_ == c))
        (acc1 + x.contains(2), acc2 + x.contains(3))
    }
    x1 * x2
  }
}
