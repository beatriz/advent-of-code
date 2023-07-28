object Day10 extends Problem[Int, Int]:
  def solve(input: String) =
    val finalState = getLines(input).foldLeft(State()) {
      case (accState, line) => Instruction(line).run(accState)
    }

    val res1 = {
      val x = finalState.result.filter(x => (x._2 - 20) % 40 == 0)
      println(x)
      x.map(y => y._1 * y._2).sum
    }

//    val (_, r1) = getLines(input).foldLeft((State(), 0)) {
//      case ((accState, accStrengths), line) =>
//        val res = Instruction(line).run(accState)
////        println(res)
//        val x = res.result.find(x => (x._2 - 20) % 40 == 0)
////        x.foreach(println)
//        val strength = x.map(y => y._2 * y._1).getOrElse(0)
//        (res, accStrengths + strength)
//    }

    (res1, 0)

  case class State(values: List[Int] = List(1)):
    lazy val curr = values.head
    def updated(newValues: List[Int]) = State(newValues ::: values)
    def updated(newValue: Int) = State(newValue :: values)
    val result = values.zipWithIndex // .map(x => (x._1, x._2 + 1))

  sealed trait Instruction:
    def run(init: State): State

  object Instruction {
    def apply(str: String): Instruction = str.split(" ") match {
      case Array("noop")    => Noop
      case Array("addx", v) => AddX(v.toInt)
    }
  }

  case object Noop extends Instruction:
    def run(init: State) = init.updated(init.curr)

  case class AddX(v: Int) extends Instruction:
    def run(init: State) =
      init.updated(List(init.curr + v, init.curr, init.curr))
