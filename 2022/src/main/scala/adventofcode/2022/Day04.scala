package adventofcode.`2022`

class Day04 extends Problem[Int, Int]:
  def solve(input: String): (Int, Int) =
    val (res1, res2) = getLines(input).foldLeft((0, 0)){
      case ((acc1, acc2), line) =>
        val pairs = line.split(",").map { p =>
          val Array(b, e) = p.split("-").map(_.toInt)
          (b, e)
        }
        val Array(smallRange, bigRange) = pairs.sortBy(p => p._2 - p._1)
        if (smallRange._1 >= bigRange._1 && smallRange._2 <= bigRange._2) (acc1 + 1, acc2 + 1)
        else if ((smallRange._1 >= bigRange._1 && smallRange._1 <= bigRange._2) || (smallRange._2 >= bigRange._1 && smallRange._2 <= bigRange._2 )) (acc1, acc2 + 1)
        else (acc1, acc2)
    }
    (res1, res2)


  def getPairs(line: String) =
    val Array(p1, p2) = line.split(",").map{ p =>
      val Array(b, e) = p.split("-").map(_.toInt)
      (b, e)
    }

    (p1, p2)
