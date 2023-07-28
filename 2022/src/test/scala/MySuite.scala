// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends munit.FunSuite {
  def testDay[A, B](dayClass: Problem[A, B], input: String, exp1: A, exp2: B) =
    val actual = dayClass.solve(input)
    assertEquals(actual, (exp1, exp2))

  test("Day01") {
    testDay(
      Day01,
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
      Day02,
      """A Y
        |B X
        |C Z""".stripMargin,
      15,
      12
    )
  }

  test("Day03") {
    testDay(
      Day03,
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
    testDay(Day04, input, 2, 4)
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
    testDay(Day05, input, "CMZ", "MCD")
  }

  test("Day06") {
    testDay(Day06, "mjqjpqmgbljsphdztnvjfqwrcgsmlb", 7, 19)
    testDay(Day06, "bvwbjplbgvbhsrlpgdmjqwftvncz", 5, 23)
    testDay(Day06, "nppdvjthqldpwncqszvftbrmjlhg", 6, 23)
    testDay(Day06, "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10, 29)
    testDay(Day06, "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11, 26)
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

    testDay(Day07, input, 95437, 24933642)
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

    testDay(Day09, input, 13, 0)
  }

  test("Day10") {
    val input = """addx 15
                  |addx -11
                  |addx 6
                  |addx -3
                  |addx 5
                  |addx -1
                  |addx -8
                  |addx 13
                  |addx 4
                  |noop
                  |addx -1
                  |addx 5
                  |addx -1
                  |addx 5
                  |addx -1
                  |addx 5
                  |addx -1
                  |addx 5
                  |addx -1
                  |addx -35
                  |addx 1
                  |addx 24
                  |addx -19
                  |addx 1
                  |addx 16
                  |addx -11
                  |noop
                  |noop
                  |addx 21
                  |addx -15
                  |noop
                  |noop
                  |addx -3
                  |addx 9
                  |addx 1
                  |addx -3
                  |addx 8
                  |addx 1
                  |addx 5
                  |noop
                  |noop
                  |noop
                  |noop
                  |noop
                  |addx -36
                  |noop
                  |addx 1
                  |addx 7
                  |noop
                  |noop
                  |noop
                  |addx 2
                  |addx 6
                  |noop
                  |noop
                  |noop
                  |noop
                  |noop
                  |addx 1
                  |noop
                  |noop
                  |addx 7
                  |addx 1
                  |noop
                  |addx -13
                  |addx 13
                  |addx 7
                  |noop
                  |addx 1
                  |addx -33
                  |noop
                  |noop
                  |noop
                  |addx 2
                  |noop
                  |noop
                  |noop
                  |addx 8
                  |noop
                  |addx -1
                  |addx 2
                  |addx 1
                  |noop
                  |addx 17
                  |addx -9
                  |addx 1
                  |addx 1
                  |addx -3
                  |addx 11
                  |noop
                  |noop
                  |addx 1
                  |noop
                  |addx 1
                  |noop
                  |noop
                  |addx -13
                  |addx -19
                  |addx 1
                  |addx 3
                  |addx 26
                  |addx -30
                  |addx 12
                  |addx -1
                  |addx 3
                  |addx 1
                  |noop
                  |noop
                  |noop
                  |addx -9
                  |addx 18
                  |addx 1
                  |addx 2
                  |noop
                  |noop
                  |addx 9
                  |noop
                  |noop
                  |noop
                  |addx -1
                  |addx 2
                  |addx -37
                  |addx 1
                  |addx 3
                  |noop
                  |addx 15
                  |addx -21
                  |addx 22
                  |addx -6
                  |addx 1
                  |noop
                  |addx 2
                  |addx 1
                  |noop
                  |addx -10
                  |noop
                  |noop
                  |addx 20
                  |addx 1
                  |addx 2
                  |addx 2
                  |addx -6
                  |addx -11
                  |noop
                  |noop
                  |noop""".stripMargin

    testDay(Day10, input, 13140, 0)
  }

  test("Day11") {
    val input = """Monkey 0:
                  |  Starting items: 79, 98
                  |  Operation: new = old * 19
                  |  Test: divisible by 23
                  |    If true: throw to monkey 2
                  |    If false: throw to monkey 3
                  |
                  |Monkey 1:
                  |  Starting items: 54, 65, 75, 74
                  |  Operation: new = old + 6
                  |  Test: divisible by 19
                  |    If true: throw to monkey 2
                  |    If false: throw to monkey 0
                  |
                  |Monkey 2:
                  |  Starting items: 79, 60, 97
                  |  Operation: new = old * old
                  |  Test: divisible by 13
                  |    If true: throw to monkey 1
                  |    If false: throw to monkey 3
                  |
                  |Monkey 3:
                  |  Starting items: 74
                  |  Operation: new = old + 3
                  |  Test: divisible by 17
                  |    If true: throw to monkey 0
                  |    If false: throw to monkey 1""".stripMargin

    testDay(Day11, input, 10605, 0)
  }

  test("Day12") {
    val input = """Sabqponm
                  |abcryxxl
                  |accszExk
                  |acctuvwj
                  |abdefghi""".stripMargin
    testDay(Day12, input, 31, 29)
  }

  test("Day13") {
    val input0 = """[1,1,3,1,1] true
                  |[1,1,5,1,1]
                  |
                  |[[1],[2,3,4]] true
                  |[[1],4]
                  |
                  |[9] false 3
                  |[[8,7,6]]
                  |
                  |[[4,4],4,4] true
                  |[[4,4],4,4,4]
                  |
                  |[7,7,7,7] false 5
                  |[7,7,7]
                  |
                  |[] true
                  |[3]
                  |
                  |[[[]]] false 7
                  |[[]]
                  |
                  |[1,[2,[3,[4,[5,6,7]]]],8,9] false 8
                  |[1,[2,[3,[4,[5,6,0]]]],8,9]""".stripMargin

    testDay(Day13, input0, 13, 140 )
  }

  test("Day14") {
    val input = """498,4 -> 498,6 -> 496,6
                  |503,4 -> 502,4 -> 502,9 -> 494,9""".stripMargin
    testDay(Day14, input, 24, 93)
  }
}
