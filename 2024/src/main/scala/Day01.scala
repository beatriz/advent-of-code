import scala.math.Ordering.Implicits._

object Day01 extends Problem[Int, Int] {
  case class CharAt(index: Int, char: Char)
  override def solve(input: String): (Int, Int) = {
    val (left, right) =
      getLines(input).foldLeft((List.empty[Int], List.empty[Int])) {
        case ((acc1, acc2), line) =>
          val chars = line.split(" {3}")
          (chars(0).toInt :: acc1, chars(1).toInt :: acc2)
      }

    val distances = left.sorted.zip(right.sorted).map { case (left, right) =>
      Math.abs(left - right)
    }

    val similarityScore = left.map(v => v * right.count(_ == v)).sum

    (distances.sum, similarityScore)
  }
}
