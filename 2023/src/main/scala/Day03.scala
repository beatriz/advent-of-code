object Day03 extends Problem[Int, Int]:
  case class Pos(x: Int, y: Int)
  case class Number(n: Int, startPos: Pos, endPos: Pos)
  case class Symbol(pos: Pos, s: Char)

  def areAdjacent(symbol: Symbol, number: Number) =
    (symbol.pos.x >= number.startPos.x - 1 && symbol.pos.x <= number.endPos.x + 1) && (symbol.pos.y >= number.startPos.y - 1 && symbol.pos.y <= number.endPos.y + 1)

  def solve(input: String): (Int, Int) =
    val numberR = """[0-9]""".r
    val (numbers, symbols) = getLines(input).zipWithIndex.foldLeft((List.empty[Number], List.empty[Symbol])) {
      case ((accN, accS), (line, y)) =>
        val c = line.zipWithIndex.foldLeft((accN, accS, Option.empty[(String, Pos)])) {
          case ((numbers, symbols, None), (c, x)) if c.isDigit => // start the current number
            (numbers, symbols, Some((c.toString, Pos(x, y))))
          case ((numbers, symbols, Some(currN)), (c, x)) if (c.isDigit && x < line.length - 1) => // continue building number
            (numbers, symbols, Some((currN._1 + c, currN._2)))
          case ((numbers, symbols, Some(currN)), (c, x)) if c.isDigit => // finish building number at end of line
            (Number((currN._1 + c).toInt, currN._2, Pos(x - 1, y)) :: numbers, symbols, None)
          case ((numbers, symbols, Some(currN)), (c, x)) if c == '.' => // end prev number
            (Number(currN._1.toInt, currN._2, Pos(x - 1, y)) :: numbers, symbols, None)
          case ((numbers, symbols, Some(currN)), (c, x)) => // end prev number and add symbol
            (Number(currN._1.toInt, currN._2, Pos(x - 1, y)) :: numbers, Symbol(Pos(x, y), c) :: symbols, None)
          case ((numbers, symbols, None), (c, x)) if c != '.' => // add symbol
            (numbers, Symbol(Pos(x, y), c) :: symbols, None)
          case (acc, _) =>
            acc
        }
        (c._1, c._2)
    }

    val partNumbers = numbers.filter { number =>
      symbols.exists(areAdjacent(_, number))
    }

    val gearRatios = symbols.foldLeft(0) {
      case (acc, s) if s.s == '*' =>
        val adjacentParts = numbers.filter(areAdjacent(s, _))
        if (adjacentParts.size == 2) acc + (adjacentParts(0).n * adjacentParts(1).n) else acc
      case (acc, _) => acc
    }

    (partNumbers.map(_.n).sum, gearRatios)
