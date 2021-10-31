package adventofcode

import scala.util.Try

class Day04 extends Problem[Int, Int] {
  val mandatoryKeys = Set("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

  def validKeys(pass: Map[String, String]) = mandatoryKeys.subsetOf(pass.keySet)

  def solve(input: String) = {
    val passports =
      input.split("\n").foldLeft(List.empty[Map[String, String]]) { case (acc, line) =>
        if (line.isEmpty) Map.empty[String, String] :: acc
        else {
          val currPass = acc.headOption.getOrElse(Map.empty[String, String])
          (currPass ++ line
            .split(" ")
            .map { entry =>
              val s = entry.split(":")
              (s(0), s(1))
            }
            .toMap) :: acc.drop(1)
        }
      }

    val res1 = passports.count(validKeys)

    val res2 = passports.count { pass =>
      validKeys(pass) && pass
        .map {
          case ("byr", v) => Try(v.toInt).fold(_ => false, value => value >= 1920 && value <= 2002)
          case ("iyr", v) => Try(v.toInt).fold(_ => false, value => value >= 2010 && value <= 2020)
          case ("eyr", v) => Try(v.toInt).fold(_ => false, value => value >= 2020 && value <= 2030)
          case ("hgt", v) =>
            v.takeRight(2) match {
              case "cm" => Try(v.dropRight(2).toInt).fold(_ => false, v => v >= 150 && v <= 193)
              case "in" => Try(v.dropRight(2).toInt).fold(_ => false, v => v >= 59 && v <= 76)
              case _    => false
            }
          case ("hcl", v) => "#[a-f0-9]{6}".r.findFirstIn(v).isDefined
          case ("ecl", v) => List("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(v)
          case ("pid", v) => v.matches("\\d{9}")
          case ("cid", _) => true
          case _          => false
        }
        .fold(true)(_ && _)
    }

    (res1, res2)
  }
}
