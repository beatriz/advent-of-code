import scala.annotation.tailrec
import scala.math.Ordering.Implicits.*

object Day02 extends Problem[Int, Int] {
  case class Report(levels: List[Int]) {
    private lazy val (diffs, _) = levels.foldLeft((List.empty[Int], Option.empty[Int])) {
      case ((acc, None), v) => (List.empty, Some(v))
      case ((acc, Some(prev)), curr) =>
        (acc :+ (prev - curr), Some(curr))
    }

    def isSafe: Boolean = {
      (diffs.forall(_ > 0) || diffs.forall(_ < 0)) && diffs.forall(v =>
        Math.abs(v) >= 1 && Math.abs(v) <= 3
      )
    }

    def isAlmostSafe: Boolean = {
      @tailrec
      def isAlmostSafeAux(currIndex: Int): Boolean = {
        if (currIndex == levels.size)
          false
        else {
          val newLevels = levels.patch(currIndex, Nil, 1)
          if (Report(newLevels).isSafe)
            true
          else isAlmostSafeAux(currIndex + 1)
        }
      }

      isAlmostSafeAux(0)
    }
  }

  object Report:
    def apply(str: String): Report =
      Report(str.split(" ").map(_.toInt).toList)
  end Report

  override def solve(input: String): (Int, Int) = {
    val reports = getLines(input).map(Report.apply)
    (reports.count(_.isSafe), reports.count(_.isAlmostSafe))
  }
}
