package com.mbeatrizmagalhaes.adventofcode

import scala.collection.mutable

import com.mbeatrizmagalhaes.adventofcode.Problem6_1.Coordinate

class Problem6_1 extends Problem[Int] {
  def minimums(xs: Seq[Coordinate], x: Int, y: Int) = {
    val f: Coordinate => Int = _.distance(x, y)
    val minimums = (Seq(xs.head) /: xs.tail) {
      (bigs, next) =>
        val fHead = f(bigs.head)
        val fNext = f(next)
        if (fHead < fNext) bigs
        else if (fHead > fNext) Seq(next)
        else bigs :+ next
    }
    if (minimums.length > 1) -1
    else minimums.head.id
  }

  override def solve(lines: List[String]) = {
    val coords = lines.zipWithIndex.map {
      case (str, index) =>
        val Array(x, y) = str.split(", ").map(_.toInt)
        Coordinate(index, x, y)
    }

    val maxX = coords.maxBy(_.x).x + 1
    val maxY = coords.maxBy(_.y).y + 1

    val matrix = mutable.Map[(Int, Int), Int]()
    val matrixRev = mutable.Map[Int, Seq[(Int, Int)]]()

    (0 to maxX).foreach{y =>
      (0 to maxY).foreach{ x =>
        val mins = minimums(coords, x, y)
        matrix((x, y)) = mins
        matrixRev(mins) = (x,y) +: matrixRev.getOrElse(mins, Seq.empty)
      }
    }

    val areas = mutable.Map[Int, Int]()
    for (i <- matrixRev.keys) {
      areas(i) = matrixRev(i).length
    }
    (0 to maxX).foreach { y =>
      (0 to maxY).foreach { x =>
        if (x == 0 || x == maxX || y == 0 || y == maxY) {
          val thisCoordOwner = matrix((x, y))
          areas(thisCoordOwner) = 0
        }
      }
    }

    areas.maxBy(_._2)._2
  }
}

object Problem6_1 {
  case class Coordinate(id: Int, x: Int, y: Int) {
    def distance(coord: Coordinate) = Math.abs(x - coord.x) + Math.abs(y - coord.y)
    def distance(x: Int, y: Int) = Math.abs(this.x - x) + Math.abs(this.y - y)
  }
}