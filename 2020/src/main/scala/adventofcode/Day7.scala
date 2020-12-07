package adventofcode

class Day7 extends Problem[Int, Int] {
  def parse(lines: Seq[String]) =
    lines.foldLeft(Map.empty[String, Set[String]]) { case (acc, line) =>
      val Array(first, second) = line.split("contain")
      val k = first.dropRight(6) // " bags "
      val v =
        if (second.contains("no other")) Set.empty[String]
        else {
          val pattern = raw"[0-9]* ([a-z\s]*) bags?".r
          second.split(",").map(pattern.findAllIn(_).group(1)).toSet
        }
      acc.updated(k, v)
    }

  def solve1(map: Map[String, Set[String]], searchingFor: Set[String], total: Set[String] = Set.empty): Set[String] = {
    val m = map.filter(_._2.intersect(searchingFor).nonEmpty).keySet
    if (m.isEmpty) total
    else solve1(map, m, total ++ m)
  }

  def solve(input: String) = {
    val map = parse(input.split("\n"))

    (solve1(map, Set("shiny gold")).size, 0)
  }
}
