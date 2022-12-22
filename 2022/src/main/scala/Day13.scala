import Day13.SimpleParser

import scala.util.parsing.combinator.*

// FIXME: This solution isn't working for my input, although it works for the example input
object Day13 extends Problem[Int, Int]:
  sealed trait Element
  case class IntElem(value: Int) extends Element
  case class ListElem(l: List[Element]) extends Element

  object SimpleParser extends RegexParsers:
    def number: Parser[IntElem] = """(0|[1-9]\d*)""".r ^^ { n =>
      IntElem(n.toInt)
    }
    def list: Parser[ListElem] = ("[" ~> repsep(element, ",") <~ "]") ^^ {
      list => ListElem(list)
    }
    def element: Parser[Element] = number | list

    def parse(input: String): Element = parse(element, input).get

  def isOrderCorrect(left: Element, right: Element): Option[Boolean] =
    (left, right) match {
      case (IntElem(l), IntElem(r)) => if (l == r) None else Some(l < r)
      case (ListElem(l), ListElem(r)) if l.isEmpty => Some(true)
      case (ListElem(l), ListElem(r)) if r.isEmpty => Some(false)
      case (ListElem(l), ListElem(r)) =>
        l.zipAll(r, ListElem(List.empty), ListElem(List.empty))
          .flatMap(p => isOrderCorrect(p._1, p._2))
          .headOption
      case (l: ListElem, r: IntElem) => isOrderCorrect(l, ListElem(List(r)))
      case (l: IntElem, r: ListElem) => isOrderCorrect(ListElem(List(l)), r)
    }

  def solve(input: String) =
    val parsed = getLines(input)
      .grouped(3)
      .map(strs => (SimpleParser.parse(strs.head), SimpleParser.parse(strs(1))))
      .toSeq

    val orders = parsed.map(p => isOrderCorrect(p._1, p._2))

    (orders.zipWithIndex.filter(_._1.contains(true)).map(_._2 + 1).sum, 0)
