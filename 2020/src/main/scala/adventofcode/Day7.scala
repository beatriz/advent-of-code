package adventofcode

class Day7 extends Problem[Int, Int] {
  case class BagInfo(n: Int, desc: String)

  def parse(lines: Seq[String]) =
    lines.foldLeft(Map.empty[String, Set[BagInfo]]) { case (acc, line) =>
      val Array(first, second) = line.split("contain")
      val k = first.dropRight(6) // " bags "
      val v =
        if (second.contains("no other")) Set.empty[BagInfo]
        else {
          val pattern = raw"([0-9]*) ([a-z\s]*) bags?".r
          second
            .split(",")
            .map { str =>
              val m = pattern.findFirstMatchIn(str).get
              BagInfo(m.group(1).toInt, m.group(2))
            }
            .toSet
        }
      acc.updated(k, v)
    }

  def solve1(map: Map[String, Set[BagInfo]], searchingFor: Set[String], total: Set[String] = Set.empty): Set[String] = {
    val m = map.filter(_._2.map(_.desc).intersect(searchingFor).nonEmpty).keySet
    if (m.isEmpty) total
    else solve1(map, m, total ++ m)
  }

  def solve2(map: Map[String, Set[BagInfo]], initial: String): Int = {
    def solve2Aux(search: BagInfo): Int = {
      val res = map.getOrElse(search.desc, Set.empty)
      if (res.isEmpty) search.n
      else search.n + search.n * (res.toList.map(r => solve2Aux(r)).sum)
    }

    solve2Aux(BagInfo(1, initial)) - 1
  }

  def solve(input: String) = {
    val map = parse(input.split("\n"))

    (solve1(map, Set("shiny gold")).size, solve2(map, "shiny gold"))
  }
}
