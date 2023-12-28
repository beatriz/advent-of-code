import scala.annotation.tailrec

object Day09 extends Problem[Int, Int]:
  def calculateHistory(line: List[Int]) =
    var x = 0
    def calcHistAux(
        currLine: List[Int],
        hist: List[List[Int]]
    ): List[List[Int]] =
      if (currLine.forall(_ == 0)) hist
      else {
        // println(hist)
        x = x + 1
        // println(currLine.sliding(2).toList)
        val nextLine = currLine.sliding(2).map(l => l(1) - l(0)).toList
        calcHistAux(nextLine, nextLine :: hist)
      }
    calcHistAux(line, List(line))

  def extrapolate(hist: List[List[Int]], isFront: Boolean) =
    hist
      .foldLeft((List.empty[Int], 0)) { case ((acc, prev), line) =>
        val value = if (isFront) line.last + prev else line.head - prev
        (value :: acc, value)
      }
      ._1
      .head

  def solve(input: String): (Int, Int) =
    val lines = getLines(input).map(_.split(' ').map(_.toInt).toList)
    lines.foldLeft((0, 0)) { case (acc, line) =>
      val history = calculateHistory(line)
      val extrapolated = extrapolate(history, true)
      val extrapolatedBack = extrapolate(history, false)
      (acc._1 + extrapolated, acc._2 + extrapolatedBack)
    }
