package adventofcode

import adventofcode.Day11._

class Day11 extends Problem[Int, Int] {
  val adjacents =
    List(Coord(-1, 1), Coord(0, 1), Coord(1, 1), Coord(-1, 0), Coord(1, 0), Coord(-1, -1), Coord(0, -1), Coord(1, -1))
  case class Coord(x: Int, y: Int) {

    def findAdjacent(chart: Seq[String]) =
      adjacents.flatMap(c => chart.getCharByCoord(x + c.x, y + c.y))

    def findFirstSeat(chart: Seq[String], dir: Coord, it: Int): Option[Char] = {
      val element = chart.getCharByCoord(x + dir.x * it, y + dir.y * it)
      if (element.fold(true)(_ != '.')) element
      else findFirstSeat(chart, dir, it + 1)
    }

    def findFirstEmpties(chart: Seq[String]) = {
      adjacents.flatMap(c => findFirstSeat(chart, c, 1))
    }
  }

  def findFinalSeatChart(grid: Seq[String], findAround: Coord => Seq[String] => List[Char], maxOccupied: Int) = {
    def findAux(curr: Seq[String], prev: Seq[String]): Seq[String] = {
      if (curr == prev) curr
      else {
        val next = curr.zipWithIndex.foldLeft(Seq.empty[String]) { case (accLs, (str, y)) =>
          val ls = str.zipWithIndex.foldLeft("") { case (accStr, (v, x)) =>
            val c = if (v == 'L') {
              val adjacents = findAround(Coord(x, y))(curr)
              if (!adjacents.contains('#')) '#' else 'L'
            } else if (v == '#') {
              val adjacents = findAround(Coord(x, y))(curr)
              if (adjacents.count(_ == '#') >= maxOccupied) 'L' else '#'
            } else v
            accStr + c
          }
          accLs :+ ls
        }
        findAux(next, curr)
      }
    }

    findAux(grid, Seq.empty)
  }

  def solve(input: String) = {
    val res1 = findFinalSeatChart(input.split("\n"), _.findAdjacent, 4)

    val res2 = findFinalSeatChart(input.split("\n"), _.findFirstEmpties, 5)

    (res1.map(_.count(_ == '#')).sum, res2.map(_.count(_ == '#')).sum)
  }
}

object Day11 {
  implicit class RichSeq(val s: Seq[String]) {
    def getByIndex(i: Int) = Option.when(s.isDefinedAt(i))(s(i))

    def getCharByCoord(x: Int, y: Int) = s.getByIndex(y).flatMap(_.getByIndex(x))
  }

  implicit class RichStr(val s: String) {
    def getByIndex(i: Int) = Option.when(s.isDefinedAt(i))(s(i))
  }
}
