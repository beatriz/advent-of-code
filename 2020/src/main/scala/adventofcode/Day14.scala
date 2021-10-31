package adventofcode

import java.math.BigInteger

import scala.util.Try

class Day14 extends Problem[Long, Int] {
  lazy val memUpdate1 = (memIndex: Int, memValue: Int, mask: Seq[(Char, Int)], memory: Map[Int, Long]) => {
    val binValue = memValue.toBinaryString.reverse.padTo(36, '0').reverse
    val maskApplied = mask.foldLeft(binValue) { case (str, (c, i)) =>
      Try(str.updated(i, c)).getOrElse(str)
    }
    val newValue = new BigInteger(maskApplied, 2).longValue()
    memory.updated(memIndex, newValue)
  }

  def run(lines: Seq[String], memUpdate: (Int, Int, Seq[(Char, Int)], Map[Int, Long]) => Map[Int, Long]) = {
    val memPattern = raw"mem\[([0-9]*)\] = ([0-9]*)".r

    val (mem, _) = lines.foldLeft((Map.empty[Int, Long], Seq.empty[(Char, Int)])) { case ((memory, mask), line) =>
      if (line.startsWith("mask")) (memory, line.drop(7).zipWithIndex.filter(_._1 != 'X'))
      else {
        line match {
          case memPattern(index, value) =>
            (memUpdate(index.toInt, value.toInt, mask, memory), mask)
          case _ =>
            println("REGEX NOT MATCHED: " + line)
            (memory, mask)
        }
      }
    }

    mem.values.sum
  }

  def solve(input: String) = {
    val lines = input.split("\n")

    val res1 = run(lines, memUpdate1)

//    auto generate_bit_variations(std::bitset<BITS> bs, Index i,
//      const std::vector<Index>& vi, std::vector<std::bitset<BITS>>& vb)
//    {
//      if (i == vi.size()) {
//        vb.push_back(bs);
//        return;
//      }
//      bs[vi[i]] = 0;
//      generate_bit_variations(bs, i+1, vi, vb);
//      bs[vi[i]] = 1;
//      generate_bit_variations(bs, i+1, vi, vb);
//    }

//    def combinations(str: Seq[Char], cache: Map[Int, Char], address: Int, value: Char) =
//      str match {
//        case Nil => cache.updated(address, value)
//        case 'X' :: t =>
//          val cache1 =
//      }

    def printAllCombinations(pattern: String, i: Int, res: Seq[String]) {
      if (i == pattern.length) {
        res :+ pattern
//        System.out.println(pattern);
      }

      // if the current character is '?'
      else if (pattern(i) == '?') {
        printAllCombinations(pattern.updated(i, '0'), i + 1, res)
        printAllCombinations(pattern.updated(i, '1'), i + 1, res)
      } else
        // if the current character is 0 or 1, ignore it and
        // recur for the remaining pattern
        printAllCombinations(pattern, i + 1, res);
    }

    (res1, 0)
  }
}
