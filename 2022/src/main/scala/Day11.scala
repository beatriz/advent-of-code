object Day11 extends Problem[Long, Long]:
  def solve(input: String) =
    val monkeyList = parse(input)
    (runNRounds(20, monkeyList), 0)

  def runNRounds(n: Int, init: Seq[Monkey]) =
    val initMap = init.zipWithIndex.map(x => x._2 -> x._1).toMap
    val finalMap = (1 to n).foldLeft(initMap) { case (currMap, i) =>
      val x = runRound(currMap)
      /*if (i % 1000 == 0 || i == 1 || i == 20)*/
      println(x.view.mapValues(_.inspected).toMap)
      x
    }
    val top1 :: top2 :: _ =
      finalMap.values.toList.sortBy(_.inspected)(Ordering[Long].reverse).take(2)
    top1.inspected * top2.inspected

  def parse(input: String): Seq[Monkey] =
    getLines(input)
      .grouped(7)
      .map { line =>
        val startingItems =
          line(1).split(":")(1).split(",").map(v => BigInt(v.trim))
        val operation = {
          val str = line(2).split("=")(1).trim.split(" ")
          Operation(Member(str(0)), Member(str(2)), Operator(str(1)))
        }
        val test = {
          val div = line(3).split(" ").takeRight(1).head.toInt
          val ifTrue = line(4).split(" ").takeRight(1).head.toInt
          val ifFalse = line(5).split(" ").takeRight(1).head.toInt
          Test(div, ifTrue, ifFalse)
        }
        Monkey(startingItems.toList, operation, test)
      }
      .toSeq

  def runRound(init: Map[Int, Monkey]) =
    def runRoundAux(
        monkeyMap: Map[Int, Monkey],
        currIndex: Int
    ): Map[Int, Monkey] =
      if (currIndex >= init.size) monkeyMap
      else
        val currMonkey = monkeyMap(currIndex)
        val nextMap = currMonkey.turn(monkeyMap, currIndex)
        runRoundAux(nextMap, currIndex + 1)

    runRoundAux(init, 0)

  case class Monkey(
      items: List[BigInt],
      op: Operation,
      test: Test,
      inspected: Long = 0,
      shouldWorryDec: Boolean = true
  ):
    def itemAfterInspection(v: BigInt) =
      if (shouldWorryDec) v / BigInt(3) else v

    def turn(currMonkeys: Map[Int, Monkey], index: Int) =
      items
        .foldLeft(currMonkeys) { case (accMonkeys, item) =>
          val newValue = itemAfterInspection(op.calculate(item))
          val whichToThrow = test.result(newValue)
          val newMonkeyItems = accMonkeys(whichToThrow).items :+ newValue
          accMonkeys.updated(
            whichToThrow,
            accMonkeys(whichToThrow).copy(items = newMonkeyItems)
          )
        }
        .updated(
          index,
          this.copy(items = Nil, inspected = inspected + items.size)
        )

  case class Operation(member1: Member, member2: Member, operator: Operator):
    def calculate(old: BigInt) =
      operator match {
        case Sum      => member1.value(old) + member2.value(old)
        case Multiply => member1.value(old) * member2.value(old)
      }

  sealed trait Member:
    def value(old: BigInt): BigInt

  object Member:
    def apply(str: String) = str match {
      case "old" => Old
      case v     => Value(BigInt(v))
    }

  case object Old extends Member:
    def value(old: BigInt) = old
  case class Value(v: BigInt) extends Member:
    def value(old: BigInt) = v

  sealed trait Operator
  object Operator:
    def apply(str: String) = str match {
      case "*" => Multiply
      case "+" => Sum
    }

  case object Sum extends Operator
  case object Multiply extends Operator

  case class Test(divisibleBy: Int, ifTrue: Int, ifFalse: Int):
    def result(value: BigInt) =
      if (value % divisibleBy == 0) ifTrue else ifFalse
