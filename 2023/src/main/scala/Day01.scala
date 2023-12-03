object Day01 extends Problem[Int, Int] {
  case class CharAt(index: Int, char: Char)  
  val numberMap = Map(
    "one" -> '1',
    "two" -> '2',
    "three" -> '3',
    "four" -> '4',
    "five" -> '5',
    "six" -> '6',
    "seven" -> '7',
    "eight" -> '8',
    "nine" -> '9'
  )

  def charsToNumber(chars: List[CharAt]) = s"${(chars.minBy(_.index).char)}${chars.maxBy(_.index).char}".toInt

  override def solve(input: String): (Int, Int) = 
    getLines(input).foldLeft((0, 0)){
        case ((acc1, acc2), line) =>
            val charsFromDigits = line.zipWithIndex.collect{ case (v, i) if v.isDigit => CharAt(i, v)}.toList
            val charsFromLetters = numberMap.flatMap{ case (k, v) => k.r.findAllMatchIn(line).map(m => CharAt(m.start, v))}.toList
            (acc1 + charsToNumber(charsFromDigits) , acc2 + charsToNumber(charsFromDigits ::: charsFromLetters))
    }
}
