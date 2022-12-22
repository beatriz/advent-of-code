object Day05 extends Problem[String, String]:

  def solve(input: String): (String, String) =
    val lines = getLines(input).filterNot(_.isEmpty)
    val (instructionsRaw, cratesRaw) = lines.partition(_.contains("move"))
    val instructions = instructionsRaw.map(parseInstruction)
    val initCrates = parseCrates(cratesRaw)

    val (rearranged1, rearranged2) =
      instructions.foldLeft((initCrates, initCrates)) {
        case ((acc1, acc2), instruction) =>
          (instruction.move(acc1, true), instruction.move(acc2, false))
      }

    (getTopCrates(rearranged1), getTopCrates(rearranged2))

  private def getTopCrates(cratesMap: Map[Int, List[String]]): String =
    cratesMap.toList.sortBy(_._1).map(_._2.head(1)).mkString

  private def parseCrates(cratesRaw: Seq[String]): Map[Int, List[String]] =
    val (cratesStrs, cratesNums) = cratesRaw.partition(_.contains("["))
    val nCrates = cratesNums.head.count(_ != ' ')
    val (crates, _) = (1 to nCrates).foldLeft(
      (Map.empty[Int, List[String]], cratesStrs.toList)
    ) { case ((accMap, accCrates), i) =>
      val split = accCrates.map(c => (c.take(4), c.drop(4)))
      val these = split.map(_._1.trim).filterNot(_.isEmpty)
      val newCrates = split.map(_._2)
      (accMap.updated(i, these), newCrates)
    }
    crates

  private def parseInstruction(str: String): Instruction =
    val split = str.split(" ")
    Instruction(split(3), split(5), split(1))

  case class Instruction(from: Int, to: Int, quantity: Int):
    def move(
        crates: Map[Int, List[String]],
        reverse: Boolean
    ): Map[Int, List[String]] =
      val toMove = crates(from).take(quantity)
      val fromPile = crates(from).drop(quantity)
      val toPile: List[String] =
        (if (reverse) toMove.reverse else toMove) ::: crates(to)
      crates.updated(from, fromPile).updated(to, toPile)

  object Instruction {
    def apply(f: String, t: String, q: String): Instruction =
      Instruction(f.toInt, t.toInt, q.toInt)
  }
