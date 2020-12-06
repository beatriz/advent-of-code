package adventofcode

class Day5 extends Problem[Int, Int] {
  case class Range(lo: Int, up: Int)
  case class Seat(row: Int, column: Int) {
    lazy val id = row * 8 + column
  }

  def nextRange(range: Range, c: Char): Either[Range, Int] = {
    val step = (range.up - range.lo) / 2

    c match {
      // upper half
      case 'B' | 'R' if range.up - range.lo == 1 =>
        Right(range.up)
      case 'B' | 'R' =>
        Left(Range(range.lo + step + 1, range.up))

      // lower half
      case 'F' | 'L' if range.up - range.lo == 1 =>
        Right(range.lo)
      case 'F' | 'L' =>
        Left(Range(range.lo, range.up - step - 1))
    }
  }

  def calculateSeat(line: String): Seat = {
    val row = line
      .take(7)
      .foldLeft[Either[Range, Int]](Left(Range(0, 127))) {
        case (Left(range), r) => nextRange(range, r)
        case (res @ Right(value), r) =>
          println(s"Reached a final value before time. Value=$value, remaining rows: $r")
          res
      }
      .getOrElse(-1)

    val column = line
      .takeRight(3)
      .foldLeft[Either[Range, Int]](Left(Range(0, 7))) {
        case (Left(range), c) => nextRange(range, c)
        case (res @ Right(value), c) =>
          println(s"Reached a final value before time. Value=$value, remaining columns: $c")
          res
      }
      .getOrElse(-1)

    Seat(row, column)
  }

  def findSeat(ids: Array[Int], previous: Int): Int = {
    if (ids.head - previous == 2) {
      println(s"previous: $previous, next: ${ids.head}")
      previous + 1
    } else findSeat(ids.tail, ids.head)
  }

  def solve(input: String) = {
    val lines = input.split("\n")
    val seats = lines.map(calculateSeat)
    val sortedIds = seats.map(_.id).sortBy(identity)

    val res1 = sortedIds.last
    val res2 = findSeat(sortedIds.tail, sortedIds.head)

    (res1, res2)
  }
}
