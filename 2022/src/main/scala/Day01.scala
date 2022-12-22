object Day01 extends Problem[Int, Int]:
  def solve(input: String) =
    val lines = getLines(input)
    val elvesCalories = lines.foldLeft(List(0)) { case (acc, line) =>
      if (line.isEmpty) 0 :: acc
      else (acc.head + line.toInt) :: acc.tail
    }

    val top3 = elvesCalories.sorted.takeRight(3)

    (top3.max, top3.sum)
