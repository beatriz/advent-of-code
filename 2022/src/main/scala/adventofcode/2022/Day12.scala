package adventofcode.`2022`

import scala.collection.mutable

class Day12 extends Problem[Int, Int]:
  case class Pos(x: Int, y: Int)

  case class Grid(values: Array[Array[Char]]):
    private lazy val posValues = values.zipWithIndex.flatMap { case (arr, y) =>
      arr.zipWithIndex.map { case (c, x) => (Pos(x, y), c) }
    }.toMap

    def findElements(value: Char) = posValues.filter(_._2 == value).keys

    def findElement(value: Char) = findElements(value).head

    def findPossibleNeighbors(origin: Pos) =
      val originValue = posValues(origin)
      def diff(or: Char, dest: Char) = (or, dest) match {
        case ('S', d) => d - 'a'
        case (o, 'E') => 'z' - o
        case (o, d)   => d - o
      }

      List(
        Pos(origin.x, origin.y - 1),
        Pos(origin.x - 1, origin.y),
        Pos(origin.x + 1, origin.y),
        Pos(origin.x, origin.y + 1)
      ).filter(p =>
        posValues.get(p).fold(false)(v => diff(originValue, v) <= 1)
      )

  def bfs(grid: Grid, startingPoint: Pos) =
    val q = mutable.Queue.empty[Pos]
    q.enqueue(startingPoint)
    val m = mutable.Map.empty[Pos, Int]
    m(startingPoint) = 0

    while (q.nonEmpty) {
      val curr = q.dequeue()
      grid.findPossibleNeighbors(curr).foreach { p =>
        if (!m.contains(p)) {
          m(p) = m(curr) + 1
          q.enqueue(p)
        }
      }
    }

    m.toMap

  def solve(input: String) =
    val grid = Grid(getLines(input).map(_.toCharArray).toArray)
    val end = grid.findElement('E')
    val startInSBfs = bfs(grid, grid.findElement('S'))
    val startInS = startInSBfs(end)
    val startInABfs = grid.findElements('a').map(p => bfs(grid, p))
    val startInA = startInABfs.flatMap(_.get(end)).toSeq

    (startInS, (startInA :+ startInS).min)
