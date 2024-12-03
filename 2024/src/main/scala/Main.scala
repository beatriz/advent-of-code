import scala.io.Source

@main def hello(problemN: String, remainingArgs: String*): Unit =
  // Scala3 still doesn't have support for optional arguments
  val isTest = remainingArgs.headOption.exists(_.toBoolean)

  val problem = Class
    .forName("Day" + problemN + "$")
    .getField("MODULE$")
    .get(classOf[Problem[?, ?]])
    .asInstanceOf[Problem[?, ?]]

  val file =
    val fileName = if (isTest) "test" else "day" + problemN
    Source.fromFile(s"input/$fileName.txt")

  val input = file.mkString
  file.close()
  val (res1, res2) = problem.solve(input)

  println(s"Day $problemN Part 1: $res1")
  println(s"Day $problemN Part 2: $res2")

trait Problem[A, B]:
  def solve(input: String): (A, B)

  def getLines(input: String): Seq[String] = input.split("\n").toIndexedSeq
