import scala.annotation.tailrec

object Day08 extends Problem[Int, Int]:
  def traverseNodes(nodes: Map[String, (String, String)], directions: String, isStartingNode: String => Boolean, isEndNode: String => Boolean): Int =
    @tailrec
    def traverseAux(currNodes: List[String], dir: List[Char], count: Int): Int =
      if (currNodes.forall(isEndNode)) count
      else {
        val nextNodes = currNodes.map{ currNode =>
          val nextPair = nodes(currNode)
          if (dir.head == 'L') nextPair._1 else nextPair._2
        }
        traverseAux(nextNodes, if (dir.tail.isEmpty) directions.toList else dir.tail, count + 1)
      }

    traverseAux(nodes.keySet.filter(isStartingNode).toList, directions.toList, 0)

  def solve(input: String): (Int, Int) =
    val lines = getLines(input)
    val instructions = lines.head

    val nodes = lines.drop(2).map { line =>
      val Array(key, pair) = line.split(" = ")
      val Array(l, r) = pair.split(", ")
      key -> (l.drop(1), r.dropRight(1))
    }.toMap


    (traverseNodes(nodes, instructions, _ == "AAA", _ == "ZZZ"), traverseNodes(nodes, instructions, _.endsWith("A"), _.endsWith("Z")))
