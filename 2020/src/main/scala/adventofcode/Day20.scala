package adventofcode

class Day20 extends Problem[Long, Long] {
  case class Tile(shape: List[String]) {
    lazy val leftBorder = shape.map(_.take(1))
    lazy val rightBorder = shape.map(_.takeRight(1))
    lazy val upBorder = shape.head
    lazy val downBorder = shape.last

    def print = shape.mkString("\n")
  }

  def solve(input: String) = {
    val tiles = input.split("\n").foldLeft(List.empty[(Int, Tile)]) { case (acc, line) =>
      if (line.isEmpty) acc
      else if (line.startsWith("Tile")) {
        val id = line.drop(5).dropRight(1).toInt
        (id, Tile(List.empty)) :: acc
      } else {
        val (currId, currTile) = acc.head
        (currId, currTile.copy(currTile.shape :+ line)) :: acc.tail
      }
    }
    println(tiles.size)
//    tiles.foreach(x => println(s"${x._1}:\n${x._2.print}"))

    val upDown = tiles.map(_._2.upBorder) ++ tiles.map(_._2.downBorder)
    println(upDown)
    println(upDown.groupBy(identity))

    val ud = upDown.groupBy(identity).map(x => (x._1, x._2.size)).filter(_._2 == 1).keys.toList
    val x = tiles.filter(x => ud.contains(x._2.upBorder) || ud.contains(x._2.downBorder))
    println(x.map(_._1))

    (0L, 0L)
  }

}
