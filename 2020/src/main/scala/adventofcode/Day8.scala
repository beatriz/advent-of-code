package adventofcode

class Day8 extends Problem[Int, Int] {
  def findLoop(lines: Array[String], acc: Int = 0, currIndex: Int = 0, visited: Set[Int] = Set.empty): Int = {
    if (visited.contains(currIndex)) acc
    else {
      val line = lines(currIndex)
      val Array(operation, argument) = line.split(" ")
      operation match {
        case "nop" => findLoop(lines, acc, currIndex + 1, visited + currIndex)
        case "acc" => findLoop(lines, acc + argument.toInt, currIndex + 1, visited + currIndex)
        case "jmp" => findLoop(lines, acc, currIndex + argument.toInt, visited + currIndex)
        case other =>
          println("invalid instruction: " + other)
          -1
      }
    }
  }

  def solve(input: String) = {

    (findLoop(input.split("\n")), 0)
  }
}
