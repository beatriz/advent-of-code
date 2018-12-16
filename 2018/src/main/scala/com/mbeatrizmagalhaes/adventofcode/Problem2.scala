package com.mbeatrizmagalhaes.adventofcode

class Problem2 extends Problems[Int, String] {
  implicit def bool2int(b:Boolean): Int = if (b) 1 else 0

  override def solve(lines: List[String]): (Int, String) = {
    val (word1, word2) = findWordsThatDifferByOne(lines.head, lines.tail)
    (checksum(lines), s"\n$word1\n$word2")
  }

  def checksum(lines: List[String]): Int = {
    val (x1, x2) = lines.foldLeft((0, 0)){
      case ((acc1, acc2), str) =>
        val x = str.distinct.map(c => str.count(_ == c))
        (acc1 + x.contains(2), acc2 + x.contains(3))
    }
    x1 * x2
  }

  def findWordsThatDifferByOne(current: String, remaining: List[String]): (String, String) = {
    remaining
      .map(x => (x, (x zip current).filter(t => t._1 != t._2)))
      .find(_._2.length == 1) match {
        case Some(str) => (current, str._1)
        case None => findWordsThatDifferByOne(remaining.head, remaining.tail)
      }
  }
}
