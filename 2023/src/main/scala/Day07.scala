import scala.math.Ordering.Implicits._

object Day07 extends Problem[Int, Int]:
  sealed trait Hand:
    def cards: List[Char]
    def priority: Int

  object Hand:
    def apply(str: String) = {
      val cards = str.toList
      val m = cards.groupBy(identity).view.mapValues(_.size).toMap

      if (m.keySet.size == 1) FiveOfAKind(cards)
      else if (m.values.exists(_ == 4)) FourOfAKind(cards)
      else if (m.keySet.size == 2 && m.values.exists(_ == 3)) FullHouse(cards)
      else if (m.values.exists(_ == 3)) ThreeOfAKind(cards)
      else if (m.keySet.size == 3) Pairs(cards)
      else if (m.values.exists(_ == 2)) Pair(cards)
      else HighCard(cards)
    }

  case class FiveOfAKind(cards: List[Char]) extends Hand:
    val priority = 1

  case class FourOfAKind(cards: List[Char]) extends Hand:
    val priority = 2
  case class FullHouse(cards: List[Char]) extends Hand:
    val priority = 3
  case class ThreeOfAKind(cards: List[Char]) extends Hand:
    val priority = 4
  case class Pairs(cards: List[Char]) extends Hand:
    val priority = 5
  case class Pair(cards: List[Char]) extends Hand:
    val priority = 6
  case class HighCard(cards: List[Char]) extends Hand:
    val priority = 7

  object HandOrdering extends Ordering[Hand] {
    val cards =
      List('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
    def compare(h1: Hand, h2: Hand) = {
      if (h1.priority == h2.priority) compareCards(h1.cards, h2.cards)
      else h1.priority compare h2.priority
    }

    def compareCards(c1: List[Char], c2: List[Char]): Int =
      if (c1.head != c2.head)
        cards.indexOf(c1.head) compare cards.indexOf(c2.head)
      else compareCards(c1.tail, c2.tail)
  }

  def solve(input: String): (Int, Int) =
    val parsed = getLines(input).map { line =>
      val Array(h, r) = line.split(" ")
      (Hand(h), r.toInt)
    }

    val handsSorted = parsed.sortBy(_._1)(HandOrdering.reverse).zipWithIndex
    val winnings = handsSorted.map { case ((_, bid), r) => bid * (r + 1) }.sum

    (winnings, 0)
