package com.mbeatrizmagalhaes.adventofcode

class Problem5_2 extends Problem[Int] {
  override def solve(lines: List[String]) = {
    val polymer = lines.head
    val distinct = polymer.toLowerCase.distinct

    val withoutUnitsSizes = distinct.map { c =>
      val withoutUnits = polymer.replace(c.toString, "").replace(c.toUpper.toString, "")
      Problem5_1.reactPolymer(withoutUnits).length
    }

    withoutUnitsSizes.min
  }
}
