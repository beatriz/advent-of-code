package com.mbeatrizmagalhaes.adventofcode

class Problem_22 extends Problem {
  implicit def bool2int(b:Boolean): Int = if (b) 1 else 0

  override def solve(lines: List[String]) = {
    val (word, char) = find(lines.head, lines.tail)
    word.replace(char, "")
  }

  def find(current: String, remaining: List[String]): (String, String) = {
    val diffList = remaining.map(x => (x, x.diff(current)))
    diffList.find(_._2.length == 1) match {
      case Some(str) => str
      case None => find(remaining.head, remaining.tail)
    }
  }
}
