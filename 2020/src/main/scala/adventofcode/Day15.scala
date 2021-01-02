package adventofcode

class Day15 extends Problem[Int, Int] {
  def play(starting: List[(Int, Int)], max: Int) = {
    val x = max * 0.15
    def go(n: Int, i: Int, mostRecentPos: Map[Int, Int]): Int =
      if (i == max) n
      else {
        if (i % x == 0) println("Going... " + i)
        val nn = mostRecentPos.get(n).fold(0)(i - _)
        go(nn, i + 1, mostRecentPos.updated(n, i))
      }

    val startingPos = starting.dropRight(1).toMap
    val (n, i) = starting.last
    go(n, i, startingPos)
  }

  def solve(input: String) = {
    val startingNums = input.split(",").map(_.toInt)

    val starting = startingNums.zipWithIndex.map { case (n, i) => (n, i + 1) }.toList

    (play(starting, 2020), play(starting, 30000000))
  }
}
