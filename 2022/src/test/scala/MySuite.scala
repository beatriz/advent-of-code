import adventofcode.`2022`._

// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends munit.FunSuite {
  def testDay[A, B](dayClass: Problem[A, B], input: String, exp1: A, exp2: B) =
    val actual = dayClass.solve(input)
    assertEquals(actual, (exp1, exp2))

  test("Day01") {
    testDay(
      new Day01(),
      """1000
        |2000
        |3000
        |
        |4000
        |
        |5000
        |6000
        |
        |7000
        |8000
        |9000
        |
        |10000""".stripMargin,
      24000,
      45000
    )

  }

  test("Day02") {
    testDay(
      new Day02(),
      """A Y
        |B X
        |C Z""".stripMargin,
      15,
      12
    )
  }

  test("Day03") {
    testDay(
      new Day03(),
      """vJrwpWtwJgWrhcsFMMfFFhFp
        |jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        |PmmdzqPrVvPwwTWBwg
        |wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        |ttgJtRGJQctTZtZT
        |CrZsJsPPZsGzwwsLwLmpwMDw""".stripMargin,
      157,
      70
    )
  }

  test("Day04") {
    val input = """2-4,6-8
                  |2-3,4-5
                  |5-7,7-9
                  |2-8,3-7
                  |6-6,4-6
                  |2-6,4-8""".stripMargin
    testDay(new Day04(), input, 2, 4)
  }

  test("Day05") {
    val input = """    [D]
                  |[N] [C]
                  |[Z] [M] [P]
                  | 1   2   3
                  |
                  |move 1 from 2 to 1
                  |move 3 from 1 to 3
                  |move 2 from 2 to 1
                  |move 1 from 1 to 2""".stripMargin
    testDay(new Day05(), input, "CMZ", "MCD")
  }

  test("Day06") {
    val c = new Day06()
    testDay(c, "mjqjpqmgbljsphdztnvjfqwrcgsmlb", 7, 19)
    testDay(c, "bvwbjplbgvbhsrlpgdmjqwftvncz", 5, 23)
    testDay(c, "nppdvjthqldpwncqszvftbrmjlhg", 6, 23)
    testDay(c, "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10, 29)
    testDay(c, "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11, 26)
  }

  test("Day07") {
    val input = """$ cd /
                  |$ ls
                  |dir a
                  |14848514 b.txt
                  |8504156 c.dat
                  |dir d
                  |$ cd a
                  |$ ls
                  |dir e
                  |29116 f
                  |2557 g
                  |62596 h.lst
                  |$ cd e
                  |$ ls
                  |584 i
                  |$ cd ..
                  |$ cd ..
                  |$ cd d
                  |$ ls
                  |4060174 j
                  |8033020 d.log
                  |5626152 d.ext
                  |7214296 k""".stripMargin

    testDay(new Day07(), input, 95437, 24933642)
  }

  test("Day09") {
    val input = """R 4
                  |U 4
                  |L 3
                  |D 1
                  |R 4
                  |D 1
                  |L 5
                  |R 2""".stripMargin

    testDay(new Day09(), input, 13, 0)
  }
}
