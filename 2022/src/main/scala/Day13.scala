import Day13.SimpleParser

import scala.util.parsing.combinator.*

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

  implicit def elementOrdering: Ordering[Element] = new Ordering[Element]:
    override def compare(left: Element, right: Element): Int =
      (left, right) match {
        case (IntElem(l), IntElem(r)) => Ordering.Int.compare(l, r)
        case (ListElem(l), ListElem(r)) if l.isEmpty && r.nonEmpty => -1
        case (ListElem(l), ListElem(r)) if r.isEmpty && l.nonEmpty => 1
        case (ListElem(l), ListElem(r)) =>
          l.zipAll(r, ListElem(List.empty), ListElem(List.empty))
            .map(p => compare(p._1, p._2))
            .find(_ != 0)
            .getOrElse(0)
        case (l: ListElem, r: IntElem) => compare(l, ListElem(List(r)))
        case (l: IntElem, r: ListElem) => compare(ListElem(List(l)), r)
      }

  def solve(input: String) =
    val allPackets = getLines(input).filter(_.nonEmpty).map(SimpleParser.parse)

    val pairsCorrectOrder = allPackets.grouped(2).toSeq.map(p => p.sorted == p)
    val correctOrderIndexes =
      pairsCorrectOrder.zipWithIndex.filter(_._1).map(_._2 + 1).sum

    val separatorsIndexes =
      val div1 = SimpleParser.parse("[[2]]")
      val div2 = SimpleParser.parse("[[6]]")
      val sortedP = (allPackets ++ List(div1, div2)).sorted.zipWithIndex
      val first = sortedP.find(_._1 == div1).get._2 + 1
      val second = sortedP.find(_._1 == div2).get._2 + 1
      first * second

    (correctOrderIndexes, separatorsIndexes)
