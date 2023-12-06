object Day04 extends Problem[Int, Int]:
  def solve(input: String): (Int, Int) =
    getLines(input).foldLeft((0, 0)) { case ((acc1, acc2), line) =>
      val Array(_, numbers) = line.split(": ")
      val Array(winningStr, mineStr) = numbers.split('|')
      val winningNrs = winningStr
        .split(' ')
        .map(_.trim())
        .filterNot(_.isEmpty())
        .map(_.toInt)
        .toList
      val mineNrs = mineStr
        .split(' ')
        .map(_.trim())
        .filterNot(_.isEmpty())
        .map(_.toInt)
        .toList
      val mineWinning = winningNrs.intersect(mineNrs)
      val score = mineWinning.foldLeft(0) { case (acc, _) =>
        if (acc == 0) 1 else acc * 2
      }

      (acc1 + score, acc2)
    }
