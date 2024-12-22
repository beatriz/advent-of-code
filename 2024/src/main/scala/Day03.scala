object Day03 extends Problem[BigInt, BigInt] {
  val mulReg = "mul\\(\\d{1,3},\\d{1,3}\\)"
  val doReg = "(do\\(\\))|(don\\'t\\(\\))"

  def mulResult(str: String): BigInt = {
    val Array(n1, n2) = str.drop("mul(".length).dropRight(1).split(",")
    BigInt(n1) * BigInt(n2)
  }
  override def solve(input: String): (BigInt, BigInt) = {
    val multiplications = getLines(input).flatMap(line =>
      mulReg.r.findAllIn(line).map(mulResult)
    )

    val (_, conditionalMultiplications) = getLines(input)
      .flatMap(line => s"($mulReg)|$doReg".r.findAllIn(line))
      .foldLeft((Option.empty[Boolean], BigInt(0))) {
        case ((_, total), "do()") =>
          (Some(true), total)
        case ((_, total), "don't()") =>
          (Some(false), total)
        case ((Some(false), total), _) =>
          (Some(false), total)
        case ((Some(true), total), mulStr) =>
          (Some(true), total + mulResult(mulStr))
        case ((None, total), mulStr) =>
          (None, total + mulResult(mulStr))
      }

    (multiplications.sum, conditionalMultiplications)
  }
}
