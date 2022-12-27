import scala.annotation.tailrec

object Day14 extends Problem[Int, Int]:
  case class Pos(x: Int, y: Int)
  def solve(input: String) =
    val wallsCoords = getLines(input).flatMap{l =>
      val parsedLine = l.split(" -> ").map { c =>
        val Array(xs, ys) = c.split(",")
        Pos(xs.toInt, ys.toInt)
      }
      parsedLine.sliding(2).map(p => (p(0), p(1))).toSeq
    }

    val walls = wallsCoords.foldLeft(Seq.empty[Pos]) {
      case (acc, (one, two)) =>
        if (one.x == two.x)
          val range = if (one.y < two.y) (one.y to two.y) else two.y to one.y
          acc ++ range.map(y => Pos(one.x, y))
        else
          val range = if (one.x < two.x) (one.x to two.x) else two.x to one.x
          acc ++ range.map(x => Pos(x, one.y))
    }.distinct

    val leftLimit = walls.maxBy(p => (-p.x, p.y))
    val rightLimit = walls.maxBy(p => (p.x, p.y))
    def isGoingToFallForever(grain: Pos, x: Seq[Pos]) =
      (grain.y > leftLimit.y && grain.x <= leftLimit.x) || (grain.y > rightLimit.y && grain.x >= rightLimit.x)


    def sandFall(shouldStopFalling: (Pos, Seq[Pos]) => Boolean, nextGrainPos: (Pos, Seq[Pos]) => Option[Pos]) =
      @tailrec
      def sandFallAux(sandCoords: Seq[Pos], currGrain: Pos, startingY: Int): Seq[Pos] =
        if (shouldStopFalling(currGrain, sandCoords)) sandCoords
        else
          nextGrainPos(currGrain, sandCoords) match {
            case None =>
              // Not entirely sure why just subtracting 1 doesn't work ¯\_(ツ)_/¯
              val newStartingY = if (currGrain.y == startingY) startingY - 1 else startingY
              sandFallAux(sandCoords :+ currGrain, Pos(500, newStartingY), newStartingY)
            case Some(next) => sandFallAux(sandCoords, next, startingY)
          }

      sandFallAux(Seq.empty, Pos(500, 0), walls.map(_.y).max - 1)

    def nextMove(currGrain: Pos, sandCoords: Seq[Pos]) =
      val possibles = List(Pos(currGrain.x, currGrain.y + 1), Pos(currGrain.x - 1, currGrain.y + 1), Pos(currGrain.x + 1, currGrain.y + 1))
      possibles.find(!(sandCoords ++ walls).contains(_))

    val floorY = walls.map(_.y).max + 2

    def nextMove2(currGrain: Pos, sandCoords: Seq[Pos]) =
      val possibles = List(Pos(currGrain.x, currGrain.y + 1), Pos(currGrain.x - 1, currGrain.y + 1), Pos(currGrain.x + 1, currGrain.y + 1))
      possibles.find(p => p.y != floorY && !(sandCoords ++ walls).contains(p))

    val first = sandFall(isGoingToFallForever, nextMove)
    // FIXME: This one takes a bit of time, would be nice to improve in the future.
    val second = sandFall((_: Pos, fallen: Seq[Pos]) => fallen.contains(Pos(500, 0)), nextMove2)
    (first.size, second.size)
