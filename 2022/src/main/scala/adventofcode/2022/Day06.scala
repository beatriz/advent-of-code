package adventofcode.`2022`

class Day06 extends Problem[Int, Int]:
  def solve(input: String) = (findStart(input, 4), findStart(input, 14))

  def findStart(str: String, n: Int) =
    def findStartAux(chars: String, value: Int): Int =
      val currStr = chars.take(n)
      if (currStr.distinct == currStr) value
      else findStartAux(chars.tail, value + 1)

    findStartAux(str.tail, n + 1)

