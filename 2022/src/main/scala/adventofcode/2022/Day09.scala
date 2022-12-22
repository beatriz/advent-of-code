package adventofcode.`2022`

class Day09 extends Problem[Int, Int]:
  def solve(input: String) =
    val finalM = getLines(input).foldLeft(Motion()) { case (m, line) =>
      val Array(dirStr, movesStr) = line.split(" ")
      m.move(Direction(dirStr), movesStr.toInt)
    }
    (finalM.visited.size, 0)

  case class Motion(head: Position, tail: Position, visited: Set[Position]):
    def move(dir: Direction, times: Int): Motion =
      (0 until times).foldLeft(this) { case (acc, _) =>
        acc.move(dir)
      }

    def move(dir: Direction): Motion =
      val newHead = head.move(dir.effect)
      val newTail = tail.follow(newHead)
      Motion(newHead, newTail, visited + newTail)

  object Motion:
    def apply(): Motion =
      Motion(Position(0, 0), Position(0, 0), Set(Position(0, 0)))

  case class Position(x: Int, y: Int):
    def -(other: Position) = Position(other.x - x, other.y - y)
    def move(other: Position) = Position(x + other.x, y + other.y)
    private def isTouching(other: Position) =
      val diff = this - other
      math.abs(diff.x) <= 1 && math.abs(diff.y) <= 1

    def follow(other: Position) =
      if (this.isTouching(other)) this
      else
        val diff = this - other
        this.move(Position(math.signum(diff.x), math.signum(diff.y)))

  sealed trait Direction:
    def effect: Position

  object Direction {
    def apply(str: String): Direction = str match {
      case "U" => Up
      case "D" => Down
      case "R" => Right
      case "L" => Left
    }
  }

  case object Up extends Direction:
    val effect = Position(0, 1)
  case object Down extends Direction:
    val effect = Position(0, -1)
  case object Right extends Direction:
    val effect = Position(1, 0)
  case object Left extends Direction:
    val effect = Position(-1, 0)
