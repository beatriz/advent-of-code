package com.mbeatrizmagalhaes.adventofcode

import org.scalatest._

class Problem5_1Spec  extends FlatSpec with Matchers {

  "Problem 5-1" should "work with test input" in {
    val input = "dabAcCaCBAcCcaDA"
    val p = new Problem5_1()
    p.solve(List(input)) should be (10)
  }
}
