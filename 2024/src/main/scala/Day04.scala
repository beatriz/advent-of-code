object Day04 extends Problem[Int, Int] {
  case class Coord(x: Int, y: Int):
    def around(maxX: Int, maxY: Int) =
      List(
        Option.when(y > 2)(
          List(Coord(x, y - 1), Coord(x, y - 2), Coord(x, y - 3))
        ),
        Option.when(y > 2 && x + 2 < maxX)(
          List(Coord(x + 1, y - 1), Coord(x + 2, y - 2), Coord(x + 3, y - 3))
        ),
        Option.when(x + 2 < maxX)(
          List(Coord(x + 1, y), Coord(x + 2, y), Coord(x + 3, y))
        ),
        Option.when(x + 2 < maxX && y + 2 < maxY)(
          List(Coord(x + 1, y + 1), Coord(x + 2, y + 2), Coord(x + 3, y + 3))
        ),
        Option.when(y + 2 < maxY)(
          List(Coord(x, y + 1), Coord(x, y + 2), Coord(x, y + 3))
        ),
        Option.when(y + 2 < maxY && x > 2)(
          List(Coord(x - 1, y + 1), Coord(x - 2, y + 2), Coord(x - 3, y + 3))
        ),
        Option.when(x > 2)(
          List(Coord(x - 1, y), Coord(x - 2, y), Coord(x - 3, y))
        ),
        Option.when(x > 2 && y > 2)(
          List(Coord(x - 1, y - 1), Coord(x - 2, y - 2), Coord(x - 3, y - 3))
        )
      ).flatten

    def diagonals(maxX: Int, maxY: Int) =
      List(
        Option.when(x < maxX && y > 0)(Coord(x + 1, y - 1)),
        Option.when(x < maxX && y < maxY)(Coord(x + 1, y + 1)),
        Option.when(x > 0 && y > 0)(Coord(x - 1, y - 1)),
        Option.when(x > 0 && y < maxY)(Coord(x - 1, y + 1))
      ).flatten

  override def solve(input: String): (Int, Int) = {
    val lines = getLines(input)
    val grid = lines.zipWithIndex.flatMap { case (str, y) =>
      str.zipWithIndex.map { case (c, x) => Coord(x, y) -> c }
    }.toMap
    val maxX = lines.head.length - 1
    val maxY = lines.size - 1
    val allX = grid.filter(_._2 == 'X').keys
    val xmasCount = allX.foldLeft(0) { case (curr, coord) =>
      curr + coord
        .around(maxX, maxY)
        .count(coords => coords.map(grid(_)) == List('M', 'A', 'S'))
    }

    val allA = grid.filter(_._2 == 'A').keys
    val x_masCount = allA.foldLeft(0) { case (curr, coord) =>
      val d = coord.diagonals(maxX, maxY)
      val m = d.filter(c => grid(c) == 'M')
      if (m.size != 2 || d.count(grid(_) == 'S') != 2) curr
      else if (m.map(_.x).distinct.size == 1 || m.map(_.y).distinct.size == 1)
        curr + 1
      else curr
    }

    (xmasCount, x_masCount)
  }
}
