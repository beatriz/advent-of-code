package adventofcode

class Day13 extends Problem[Int, Int] {
  def solve(input: String) = {
    val lines = input.split("\n")
    val earliest = lines(0).toInt
    val buses = lines(1).split(",").filterNot(_ == "x").map(_.toInt)

    val valid = buses.map { id =>
      val below = earliest / id
      val mult = id * (below + 1)
      println(s"bus: $id, below: $below, mult: $mult")
      (id, mult - earliest)
    }

    val busToTake = valid.minBy(_._2)
    println(valid.minBy(_._2))

    (busToTake._1 * busToTake._2, 0)
  }
}
