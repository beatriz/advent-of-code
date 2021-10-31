package adventofcode

class Day09 extends Problem[Long, Long] {
  def findInvalid(
      preamble: Seq[Long],
      curr: Long,
      remainingLine: Seq[Long],
      numbersUntilCurr: Seq[Long]
  ): (Long, Seq[Long]) = {
    def findInPreamble(p: Set[Long]): Boolean = {
      if (p.isEmpty) false
      else if (p.tail.contains(curr - p.head)) true
      else findInPreamble(p.tail)
    }

    if (!findInPreamble(preamble.toSet)) (curr, numbersUntilCurr)
    else findInvalid(preamble.tail :+ curr, remainingLine.head, remainingLine.tail, numbersUntilCurr :+ curr)
  }

  def findSum(list: Seq[Long], sumTo: Long): Seq[Long] = {
    def sum(remainingList: Seq[Long], curr: Long, currList: Seq[Long]): Seq[Long] = {
      remainingList match {
        case Nil                           => Seq.empty
        case x :: Nil if curr + x == sumTo => x +: currList
        case x :: Nil                      => Seq.empty
        case x :: xs if curr + x == sumTo  => x +: currList
        case x :: xs if curr + x > sumTo   => Seq.empty
        case x :: xs                       => sum(xs, curr + x, x +: currList)
      }
    }

    val currSum = sum(list, 0, Seq.empty)
    if (currSum.isEmpty) findSum(list.tail, sumTo)
    else currSum
  }

  def solve(input: String) = {
    val lines = input.split("\n").map(_.toLong)
    val preambleSize = 25
    val initialPreamble = lines.take(preambleSize)

    val (invalid, numbersUntilInvalid) =
      findInvalid(initialPreamble, lines.drop(preambleSize).take(1).head, lines.drop(preambleSize + 1), initialPreamble)

    val listSum = findSum(numbersUntilInvalid.toList, invalid)

    (invalid, listSum.min + listSum.max)
  }
}
