package adventofcode

class Day21 extends Problem[Long, String] {

  def solve(input: String) = {
    val (ingredientsPerAlergen, allIngredients) =
      input.split("\n").foldLeft((Map.empty[String, Set[String]], Map.empty[String, Int])) {
        case ((accMap, accIng), line) =>
          val Array(ing, rest) = line.split("\\(contains")
          val ingredients = ing.split(" ").toSet
          val alergens = rest.drop(1).dropRight(1).split(", ")

          val newMap = alergens.foldLeft(accMap) { case (alMap, alerg) =>
            val newIng = alMap.get(alerg).fold(ingredients) { existingIngredients =>
              ingredients.intersect(existingIngredients)
            }
            alMap.updated(alerg, newIng)
          }

          val newIng = ingredients.foldLeft(accIng) { case (ingMap, ing) =>
            val ingCount = ingMap.getOrElse(ing, 0) + 1
            ingMap.updated(ing, ingCount)
          }

          (newMap, newIng)
      }

    def go(
        remaining: Map[String, Set[String]],
        alergIngMap: Map[String, String] = Map.empty,
        mostRecent: Set[String] = Set.empty
    ): Map[String, String] = {
      if (remaining.isEmpty) alergIngMap
      else {
        val thisRemaining = remaining.map { case (k, v) => (k, v -- mostRecent) }
        val found = thisRemaining.filter(_._2.size == 1).map { case (k, v) => (k, v.head) }
        if (found.isEmpty) {
          println("ERROR, no empty found")
        }
        go(thisRemaining -- found.keySet, alergIngMap ++ found, found.values.toSet)
      }
    }

    val alergenIngredient = go(ingredientsPerAlergen)

    val res1 = (allIngredients -- alergenIngredient.values.toSet).values.sum
    val res2 = alergenIngredient.toSeq.sortBy(_._1).map(_._2).mkString(",")

    (res1, res2)
  }

}
