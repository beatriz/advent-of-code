object Day02 extends Problem[Int, Int] {
  case class Cubes(red: Int, green: Int, blue: Int):
    def isValid = red <= 12 && green <= 13 && blue <= 14
    def power = red * green * blue

  object Cubes:
    def fromString(str: String): Cubes =
      val parsed = str
        .split(',')
        .map { s =>
          val Array(n, color) = s.trim().split(' ')
          color -> n.trim().toInt
        }
        .toMap
      Cubes(
        parsed.getOrElse("red", 0),
        parsed.getOrElse("green", 0),
        parsed.getOrElse("blue", 0)
      )

  case class Game(index: Int, record: List[Cubes])

  override def solve(input: String): (Int, Int) =
    val games = getLines(input).map { line =>
      val Array(header, body) = line.split(':')
      val cubes = body.split(';').map(Cubes.fromString).toList
      val i = header.substring("Game ".size).toInt
      Game(i, cubes)
    }

    val validGamesIndexes = games.collect {
      case Game(i, r) if !r.exists(!_.isValid) => i
    }

    val minCubesPerDay = games.map(g =>
      Cubes(
        g.record.map(_.red).max,
        g.record.map(_.green).max,
        g.record.map(_.blue).max
      )
    )
    (validGamesIndexes.sum, minCubesPerDay.map(_.power).sum)
}
