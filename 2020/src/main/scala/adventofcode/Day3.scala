package adventofcode

class Day3 extends Problem[Long, Long] {
  def treesOnSlope(xAdvance: Int, yAdvance: Int, input: Seq[String]) = {
    val (res, _) = input.zipWithIndex.foldLeft(0L, 1) { 
      case ((accTrees, accX), (line, i)) =>
        if (i == 0 || i % yAdvance != 0) 
          (accTrees, accX)
        else {
          val x = accX + xAdvance
          val zeroBasedX = (x - 1) % line.size
          val trees = if (line.charAt(zeroBasedX) == '#') accTrees + 1 else accTrees
          (trees, x)
        }
    }
    res
  }

  def solve(input: Seq[String]) = {
    val slopes = input.map(line => line.map(c => if (c == '#') 1 else 0))
    val res1 = treesOnSlope(3, 1, input)
    val res2: Long = {
      val r11 = treesOnSlope(1, 1, input)
      val r51 = treesOnSlope(5, 1, input)
      val r71 = treesOnSlope(7, 1, input)
      val r12 = treesOnSlope(1, 2, input)
      println(s"11= $r11, 51= $r51, 71=$r71, 21= $r12")
      res1 * r11 * r51 * r71 * r12: Long
    }
    (res1, res2)
  }
}
