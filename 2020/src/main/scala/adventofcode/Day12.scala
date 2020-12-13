package adventofcode

class Day12 extends Problem[Int, Int] {
  def normalizeDir(d: Int) = if (d < 0) d + 360 else d % 360

  sealed trait Instruction {
    def move1(ship: ShipLocation): ShipLocation

    def move2(ship: ShipLocation, waypoint: ShipLocation): (ShipLocation, ShipLocation)
  }

  case class N(value: Int) extends Instruction {
    private def move(ship: ShipLocation) = ship.copy(y = ship.y + value)

    def move1(ship: ShipLocation) = move(ship)

    def move2(ship: ShipLocation, waypoint: ShipLocation): (ShipLocation, ShipLocation) = (ship, move(waypoint))
  }

  case class S(value: Int) extends Instruction {
    private def move(ship: ShipLocation) = ship.copy(y = ship.y - value)

    def move1(ship: ShipLocation) = move(ship)

    def move2(ship: ShipLocation, waypoint: ShipLocation): (ShipLocation, ShipLocation) = (ship, move(waypoint))
  }

  case class E(value: Int) extends Instruction {
    private def move(ship: ShipLocation) = ship.copy(x = ship.x + value)

    def move1(ship: ShipLocation) = move(ship)

    def move2(ship: ShipLocation, waypoint: ShipLocation): (ShipLocation, ShipLocation) = (ship, move(waypoint))
  }

  case class W(value: Int) extends Instruction {
    private def move(ship: ShipLocation) = ship.copy(x = ship.x - value)

    def move1(ship: ShipLocation) = move(ship)

    def move2(ship: ShipLocation, waypoint: ShipLocation): (ShipLocation, ShipLocation) = (ship, move(waypoint))
  }

  case class R(value: Int) extends Instruction {
    def move1(ship: ShipLocation) =
      ship.copy(dir = normalizeDir(ship.dir - value))

    def move2(ship: ShipLocation, waypoint: ShipLocation): (ShipLocation, ShipLocation) =
      (ship, waypoint.rotateWaypoint(value))
  }

  case class L(value: Int) extends Instruction {
    def move1(ship: ShipLocation) =
      ship.copy(dir = normalizeDir(ship.dir + value))

    def move2(ship: ShipLocation, waypoint: ShipLocation): (ShipLocation, ShipLocation) =
      (ship, waypoint.rotateWaypoint(-value))
  }

  case class F(value: Int) extends Instruction {
    private def dirToMove(dir: Int): Int => Instruction = dir match {
      case 0   => E
      case 90  => N
      case 180 => W
      case 270 => S
    }

    def move1(ship: ShipLocation) =
      dirToMove(ship.dir)(value).move1(ship)

    def move2(ship: ShipLocation, waypoint: ShipLocation): (ShipLocation, ShipLocation) = {
      (ship.copy(ship.x + value * waypoint.x, ship.y + value * waypoint.y), waypoint)
    }
  }

  case class ShipLocation(x: Int, y: Int, dir: Int) {
    def rotateWaypoint(value: Int): ShipLocation =
      if (value == 0) this
      else if (value > 0) this.copy(y, -x).rotateWaypoint(value - 90)
      else this.copy(-y, x).rotateWaypoint(value + 90)

    def manhattan = Math.abs(x) + Math.abs(y)
  }

  def solve(input: String) = {
    val instructions = input.split("\n").map { str =>
      val value = str.drop(1).toInt
      str(0) match {
        case 'N' => N(value)
        case 'S' => S(value)
        case 'E' => E(value)
        case 'W' => W(value)
        case 'F' => F(value)
        case 'R' => R(value)
        case 'L' => L(value)
      }
    }

    val res1 = instructions
      .foldLeft(ShipLocation(0, 0, 0)) { case (curr, inst) => inst.move1(curr) }

    val (res2, _) = instructions
      .foldLeft((ShipLocation(0, 0, 0), ShipLocation(10, 1, 0))) { case ((s, w), inst) =>
        inst.move2(s, w)
      }

    (res1.manhattan, res2.manhattan)
  }
}
