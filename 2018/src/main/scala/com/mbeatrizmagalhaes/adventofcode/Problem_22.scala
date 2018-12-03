package com.mbeatrizmagalhaes.adventofcode

class Problem_22 extends Problem {
  implicit def bool2int(b:Boolean): Int = if (b) 1 else 0

  override def solve(lines: List[String]) = {
    val (word1, word2) = find(lines.head, lines.tail)
    word1 + " " + word2
  }

  def find(current: String, remaining: List[String]): (String, String) = {
    remaining
      .map(x => (x, (x zip current).filter(t => t._1 != t._2)))
      .find(_._2.length == 1) match {
        case Some(str) => (current, str._1)
        case None => find(remaining.head, remaining.tail)
      }
  }
}
