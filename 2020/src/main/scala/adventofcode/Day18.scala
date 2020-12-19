package adventofcode

import scala.util.parsing.combinator.RegexParsers

class Day18 extends Problem[Long, Long] {
  sealed trait Expr {
    def value: Long
  }
  case class Num(x: Long) extends Expr {
    lazy val value = x
  }
  case class Sum(a: Expr, b: Expr) extends Expr {
    lazy val value = a.value + b.value
  }
  case class Product(a: Expr, b: Expr) extends Expr {
    lazy val value = a.value * b.value
  }

  trait BaseParser extends RegexParsers {
    def expr: Parser[Expr]
    def number: Parser[Num] = """(0|[1-9]\d*)""".r ^^ { x => Num(x.toLong) }
    def term: Parser[Expr] = number | "(" ~> expr <~ ")"
    def sum: Parser[Expr => Expr] = "+" ~> term ^^ { b => Sum(_, b) }

    def parse(input: String): Expr = parse(expr, input).get
  }

  object Parser1 extends BaseParser {
    def prod: Parser[Expr => Expr] = "*" ~> term ^^ { b => Product(_, b) }
    def expr: Parser[Expr] = term ~ rep(sum | prod) ^^ { case number ~ list =>
      list.foldLeft(number) { case (acc, curr) => curr(acc) }
    }
  }

  object Parser2 extends BaseParser {
    def prod: Parser[Expr => Expr] = "*" ~> factor ^^ { b => Product(_, b) }
    def factor: Parser[Expr] = term ~ rep(sum) ^^ { case number ~ list =>
      list.foldLeft(number) { case (acc, curr) => curr(acc) }
    }
    def expr: Parser[Expr] = factor ~ rep(prod) ^^ { case number ~ list =>
      list.foldLeft(number) { case (acc, curr) => curr(acc) }
    }
  }

  def solve(input: String) = {
    val res1 = input.split("\n").foldLeft(0L) { case (acc, str) => acc + Parser1.parse(str).value }
    val res2 = input.split("\n").foldLeft(0L) { case (acc, str) => acc + Parser2.parse(str).value }
    (res1, res2)
  }
}
