package com.mbeatrizmagalhaes.adventofcode

class Problem7_1 extends Problem[String] {
  override def solve(lines: List[String]) = {
    val stepsDependencies: Map[String, List[String]] = lines.map{ l =>
      val s = l.split(" ")
      (s(1), s(7))
    }.groupBy(_._1).map { case (k,v) => (k,v.map(_._2))}

    def runSteps(stepDeps: Map[String, List[String]], allSteps: Set[String], orderOfSteps: Seq[String]): Seq[String] =
      if (allSteps.isEmpty) orderOfSteps
      else {
        val deps: Set[String] = stepDeps.values.flatten.toSet
        val nextStep: String = stepDeps.keys.filter(s => !deps.contains(s)).toSeq.sorted.headOption.getOrElse(allSteps.head)
        runSteps(stepDeps.filter(_._1 != nextStep), allSteps.filter(_ != nextStep), orderOfSteps :+ nextStep)
      }

    val allSteps = stepsDependencies.keys.toSet ++ stepsDependencies.values.flatten.toSet
    runSteps(stepsDependencies, allSteps, Seq.empty).mkString
  }
}

