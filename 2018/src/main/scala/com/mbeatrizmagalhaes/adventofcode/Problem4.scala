package com.mbeatrizmagalhaes.adventofcode

class Problem4 extends Problems[Int, Int] {
  def getMinutes(line: String) = {
    val minMatch = "\\:(.*?)\\]".r
    minMatch.findFirstIn(line).get.drop(1).dropRight(1).toInt
  }

  def getId(line: String) = {
    val idMatch = "#(\\d*)".r
    idMatch.findFirstIn(line).map(_.drop(1)).get
  }

  def mapWithMinutes(lines: List[String]): Map[String, List[Int]] =
    lines.sorted.foldRight(List(List[String]())) {
      (x, acc) =>
        if (x.contains("begins shift")) List() :: (x :: acc.head) :: acc.tail
        else (x :: acc.head) :: acc.tail
    }.tail.map{ x =>
    val list = x.tail.map(getMinutes).grouped(2).flatMap(pair => (pair.head until pair(1)).toList).toList
    (getId(x.head), list)
  }.groupBy(_._1).map { case (k,v) => (k,v.flatMap(_._2))}

  def minutesListToCountMap(min: List[String]) = min.groupBy(identity).mapValues(_.size)

  override def solve(lines: List[String]) = {
    val mapMinutes = mapWithMinutes(lines)

    def part1 = {
      val maxId = mapMinutes.mapValues(_.size).maxBy(_._2)._1
      val maxMinute = mapMinutes(maxId).groupBy(identity).mapValues(_.size).maxBy(_._2)._1
      maxId.toInt * maxMinute
    }

    def part2 = {
      val maxGuardWithMinutes = mapMinutes.filter(_._2.nonEmpty).mapValues(_.groupBy(identity).mapValues(_.size).maxBy(_._2)).maxBy(_._2._2)
      val maxId = maxGuardWithMinutes._1
      val maxMinute = maxGuardWithMinutes._2._1
      maxId.toInt * maxMinute
    }

    (part1, part2)
  }

}
