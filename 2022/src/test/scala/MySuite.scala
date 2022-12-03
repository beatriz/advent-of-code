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
}
