package adventofcode

class Day10 extends Problem[Int, Long] {

  def findDifferences(jolts: Seq[Int]): Map[Int, Int] = {
    def findDifferencesAux(j: Seq[Int], curr: Int, diffs: Map[Int, Int]): Map[Int, Int] =
      j match {
        case Nil => diffs
        case x :: xs =>
          val diff = x - curr
          if (diff > 3) println(s"found difference > 3 between $curr and $x")
          // println(s"diff between $curr and $x: $diff")
          val newDiffs = diffs.updated(diff, 1 + diffs.getOrElse(diff, 0))
          findDifferencesAux(xs, x, newDiffs)
      }

    findDifferencesAux(jolts, 0, Map.empty)
  }

  def findArrangements(jolts: Seq[Int]) = {
    val solutions = collection.mutable.Map[Int, Long]()

    def f(jolt: Int): Long =
      if (jolt == jolts.max) 1
      else if (!jolts.contains(jolt)) 0
      else {
        val a = solutions.getOrElse(jolt + 1, f(jolt + 1))
        val b = solutions.getOrElse(jolt + 2, f(jolt + 2))
        val c = solutions.getOrElse(jolt + 3, f(jolt + 3))
        solutions.update(jolt + 1, a)
        solutions.update(jolt + 2, b)
        solutions.update(jolt + 3, c)
        a + b + c
      }

    f(1) + f(2) + f(3)
  }

  def solve(input: String) = {
    val lines = input.split("\n").map(_.toInt).toList
    val jolts = (lines :+ (lines.max + 3)).sorted

    val differences = findDifferences(jolts)

    (differences.get(1).get * differences.get(3).get, findArrangements(jolts))
  }
}
