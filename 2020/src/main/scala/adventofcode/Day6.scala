package adventofcode

class Day6 extends Problem[Int, Int] {
  def solve(input: String) = {
    val groups = input.split("\n").foldLeft(List.empty[List[String]]) {
      case (acc, line) =>
        if (line.isEmpty) List.empty[String] :: acc
        else (line :: acc.headOption.getOrElse(List.empty[String])) :: acc.drop(1)
    }

    val res2 = groups.map { group =>
      group.tail.foldLeft(group.head) {
        case (acc, g) => acc.filter(g.toSet)
      }.size
    }.sum

    (groups.map(_.flatMap(_.toList).toSet.size).sum, res2)
  }
}
