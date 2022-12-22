package adventofcode.`2022`

import adventofcode.`2022`.Day02.{HandShape, Result}

class Day02 extends Problem[Int, Int]:
  def solve(input: String): (Int, Int) =
    val lines = getLines(input)
    val parsed = lines.map { str =>
      val Array(theirs, mine) = str.split(" ")
      (HandShape(theirs), HandShape(mine), Result(mine))
    }

    val (score1, score2) = parsed.foldLeft((0, 0)) {
      case ((acc1, acc2), (theirs, mine, result)) =>
        (
          acc1 + mine.battle(theirs).score + mine.score,
          acc2 + result.shapeNeeded(theirs).score + result.score
        )
    }

    (score1, score2)

object Day02:
  sealed trait HandShape:
    def beats: HandShape
    def score: Int
    def battle(other: HandShape): Result =
      if (this.beats == other) Win
      else if (this == other) Draw
      else Loss

  case object Rock extends HandShape:
    val beats = Scissor
    val score = 1
  case object Paper extends HandShape:
    val beats = Rock
    val score = 2
  case object Scissor extends HandShape:
    val beats = Paper
    val score = 3

  object HandShape:
    def apply(str: String): HandShape = str match
      case "A" | "X" => Rock
      case "B" | "Y" => Paper
      case "C" | "Z" => Scissor

  sealed trait Result:
    def score: Int
    def shapeNeeded(other: HandShape): HandShape

  case object Win extends Result:
    val score = 6
    def shapeNeeded(other: HandShape) = other match {
      case Paper   => Scissor
      case Scissor => Rock
      case Rock    => Paper
    }
  case object Draw extends Result:
    val score = 3
    def shapeNeeded(other: HandShape) = other
  case object Loss extends Result:
    val score = 0
    def shapeNeeded(other: HandShape) = other.beats

  object Result:
    def apply(str: String): Result = str match
      case "X" => Loss
      case "Y" => Draw
      case "Z" => Win
