package adventofcode

class Day17 extends Problem[Int, Int] {
  case class Coord(x: Int, y: Int, z: Int)

  case class Plan(values: Map[Coord, Boolean], xSize: Int, ySize: Int, zSize: Int)

  def addZLayer(plan: Plan) = {
    val newVals = for {
      x <- 0 to plan.xSize
      y <- 0 to plan.ySize
      z <- List(plan.zSize + 1, -plan.zSize - 1)
    } yield Coord(x, y, z) -> false

    plan.copy(values = plan.values ++ newVals, zSize = plan.zSize + 1)
  }

  def addXLayer(plan: Plan) = {
    val newVals = for {
      x <- 0 to plan.xSize
      y <- 0 to plan.ySize
      z <- List(plan.zSize + 1, -plan.zSize - 1)
    } yield Coord(x, y, z) -> false

    plan.copy(values = plan.values ++ newVals, zSize = plan.zSize + 1)
  }

  def solve(input: String) = {
    val initial = input
      .split("\n")
      .zipWithIndex
      .flatMap { case (str, y) =>
        str.toCharArray.zipWithIndex.map {
          case ('#', x) => Coord(x, y, 0) -> true
          case ('.', x) => Coord(x, y, 0) -> false
        }
      }
      .toMap

    (0, 0)
  }
}
