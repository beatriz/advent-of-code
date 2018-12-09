package com.mbeatrizmagalhaes.adventofcode

import com.mbeatrizmagalhaes.adventofcode.Problem6_1.Coordinate

class Problem6_2 extends Problem[Int] {
  override def solve(lines: List[String]) = {
    val coords = lines.zipWithIndex.map {
      case (str, index) =>
        val Array(x, y) = str.split(", ").map(_.toInt)
        Coordinate(index, x, y)
    }

    val maxX = coords.maxBy(_.x).x + 1
    val maxY = coords.maxBy(_.y).y + 1

    var area = 0
    (0 to maxX).foreach{ y =>
      (0 to maxY).foreach{ x =>
        val sum = coords.map(_.distance(x,y)).sum
        if (sum < 10000) area += 1
      }
    }

    area
  }
}

