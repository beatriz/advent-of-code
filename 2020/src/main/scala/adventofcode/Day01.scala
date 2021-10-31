package adventofcode

class Day01 extends Problem[Int, Int] {

  def solve1(values: Seq[Int]): (Int, Int) = values match {
    case Nil => (0, 0)
    case h :: t =>
      t.map(x => (x, x + h)).find(_._2 == 2020) match {
        case Some((x, s)) => (h, x)
        case None         => solve1(t)
      }
  }

  def solve(input: String) = {
    val lines = input.split("\n").map(_.toInt)

    val res1 = solve1(lines)
    (
      res1._1 * res1._2,
      lines.combinations(3).toList.map(x => (x, x.sum)).find(_._2 == 2020).map(_._1.product).getOrElse(0)
    )
  }
}
