package adventofcode

class Day2 extends Problem[Int, Int] {
  def parse(str: String): (Int, Int, Char, String) = {
    val min = "^(\\d*)".r.findFirstIn(str).get.toInt
    val max = "\\-(\\d*)".r.findFirstIn(str).get.drop(1).toInt
    val char = "([a-z]*)\\:".r.findFirstIn(str).get.head
    val pass = "\\:\\s([a-z]*)".r.findFirstIn(str).get.drop(2)
    (min, max, char, pass)
  }

  def solve(input: String): (Int, Int) = {
    input.split("\n").foldLeft((0, 0)) {
      case ((acc1, acc2), str) =>
        val (min, max, char, pass) = parse(str)
        val isValid1 = {
          val existingChars = pass.count(_ == char)
          existingChars >= min && existingChars <= max
        }

        val isValid2 = {
          val char1 = pass(min-1)
          val char2 = pass(max-1)
          (char1 == char, char2 == char) match {
            case (true, true) => false
            case (false, false) => false
            case _ => true
          }
        }

        (acc1 + (if(isValid1) 1 else 0), acc2 + (if(isValid2) 1 else 0))
    }
  }
}
