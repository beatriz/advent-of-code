package com.mbeatrizmagalhaes.adventofcode

import org.scalatest._

class Problem5_2Spec  extends FlatSpec with Matchers {

  "Problem 5-2" should "work with test input" in {
    val input = "dabAcCaCBAcCcaDA"
    val p = new Problem5_2()
    p.solve(List(input)) should be (4)
  }
}
