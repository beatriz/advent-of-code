package adventofcode.`2022`

object Day03 extends Problem[Int, Int]:
  def solve(input: String): (Int, Int) =
    val res1 = getLines(input).foldLeft(0) { case (acc, line) =>
      acc + itemPriority(getRepeatedItem(line).head)
    }

    val res2 = getLines(input).grouped(3).toSeq.foldLeft(0) {
      case (acc, group) => acc + itemPriority(getBadgeType(group).head)
    }

    (res1, res2)

  def getRepeatedItem(rucksacks: String) =
    val (ruc1, ruc2) = rucksacks.splitAt(rucksacks.length / 2)
    ruc1.intersect(ruc2)

  def getBadgeType(group: Seq[String]) =
    group.tail.fold(group.head)(_.intersect(_))

  def itemPriority(item: Char) =
    if (item.isUpper) item.hashCode - 38 else item.hashCode - 96
