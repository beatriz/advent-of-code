object Day15 extends Problem[Int, Int]{
  def hash(str: String) = 
    str.foldLeft(0){case (acc, c) =>
      val ascii = c.toInt
      val s1 = acc + ascii
      val s2 = s1 * 17
      s2 % 256  
    }


  val dashRegex = """([a-z]*)-""".r
  val equalsR = """([a-z]*)=([0-9])""".r
  def hashMapStep(step: String, lenses: Map[Int, List[(String, Int)]]): Map[Int, List[(String, Int)]] = step match {
    case dashRegex(label) => 
      val boxN = hash(label)
      lenses.get(boxN).fold(lenses)(box => lenses.updated(boxN, box.filterNot(_._1 == label)))
    case equalsR(label, nStr) => 
      val boxN = hash(label)
      val n = nStr.toInt
      val updatedBox = lenses.get(boxN).fold(List((label, n))){box =>
        box.indexWhere(_._1 == label) match {
          case -1 => box :+ (label, n)
          case i => box.updated(i, (label, n))
        }
      }
      lenses.updated(boxN, updatedBox)
  }

  def calculateFocusingPower(lenses: Map[Int, List[(String, Int)]]) =
    lenses.filterNot(_._2.isEmpty).map { case (boxN, lenses) =>
      lenses.zipWithIndex.map { case ((label, focalLength), i) =>
        val c1 = 1 + boxN
        val c2 = 1 + i
        val c3 = focalLength

        c1 * c2 * c3
      }
    }.flatten.sum

  def solve(input: String): (Int, Int) = {
    val steps = input.split(',')
    val stepHashes = steps.map(hash)

    val lensesBoxes = steps.foldLeft(Map.empty[Int, List[(String, Int)]]){
      case (acc, step) => hashMapStep(step, acc)
    }

    (stepHashes.sum, calculateFocusingPower(lensesBoxes))
  }
}