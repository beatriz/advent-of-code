// Part 2 is super slow :\
object Day05 extends Problem[Long, Long]:
  case class CategoryRangeMap(
      sourceStart: Long,
      destinationStart: Long,
      rangeLength: Long
  ):
    def destinationValue(source: Long): Option[Long] =
      if (inRange(source, (sourceStart, sourceStart + rangeLength))) {
        val i = source - sourceStart
        Some(destinationStart + i)
      } else None

  def findIdFromList(value: Long, rangeMaps: List[CategoryRangeMap]): Long =
    if (rangeMaps.isEmpty) value
    else {
      val currRangeMap = rangeMaps.head
      currRangeMap
        .destinationValue(value)
        .fold(findIdFromList(value, rangeMaps.tail))(identity)
    }

  case class AlmanacInfo(
      seed: Long,
      soil: Long,
      fertilizer: Long,
      water: Long,
      light: Long,
      temp: Long,
      hum: Long,
      loc: Long
  )

  object AlmanacInfo:
    def fromSeed(seed: Long, maps: Map[String, List[CategoryRangeMap]]) = {
      val soil =
        findIdFromList(seed, maps.getOrElse("seed-to-soil", List.empty))
      val fert =
        findIdFromList(soil, maps.getOrElse("soil-to-fertilizer", List.empty))
      val water =
        findIdFromList(fert, maps.getOrElse("fertilizer-to-water", List.empty))
      val light =
        findIdFromList(water, maps.getOrElse("water-to-light", List.empty))
      val temp = findIdFromList(
        light,
        maps.getOrElse("light-to-temperature", List.empty)
      )
      val hum = findIdFromList(
        temp,
        maps.getOrElse("temperature-to-humidity", List.empty)
      )
      val loc =
        findIdFromList(hum, maps.getOrElse("humidity-to-location", List.empty))
      AlmanacInfo(seed, soil, fert, water, light, temp, hum, loc)
    }

  case class ParsedInfo(
      seeds: List[Long],
      maps: Map[String, List[CategoryRangeMap]]
  ):
    def findMinLocation =
      seeds.foldLeft(Long.MaxValue) { case (currMin, seed) =>
        // println((seed, AlmanacInfo.fromSeed(seed, maps)))
        Math.min(currMin, AlmanacInfo.fromSeed(seed, maps).loc)
      }

  def inRange(value: Long, range: (Long, Long)): Boolean =
    value < (range._1 + range._2) && value >= range._1

  def solve(input: String): (Long, Long) =
    val (parsedInfo, _) =
      getLines(input).foldLeft((ParsedInfo(List.empty, Map.empty), "")) {
        case ((acc, currMapTitle), line) =>
          if (line.startsWith("seeds:")) {
            val seeds =
              line.substring("seeds: ".size).split(" ").map(_.toLong).toList
            (acc.copy(seeds = seeds), "")
          } else if (line.contains("map:")) {
            (acc, line.split(" ").head)
          } else if (line.isEmpty()) {
            (acc, currMapTitle)
          } else {
            val currMaps = acc.maps.getOrElse(currMapTitle, List.empty)
            val Array(d, s, r) = line.split(" ").map(_.toLong)
            val maps = acc.maps.updated(
              currMapTitle,
              CategoryRangeMap(s, d, r) :: currMaps
            )
            (acc.copy(maps = maps), currMapTitle)
          }
      }

    val newSeeds = parsedInfo.seeds.grouped(2).map(l => (l.head, l(1))).toList

    def findMinLocForRanges(
        ranges: List[(Long, Long)],
        maps: Map[String, List[CategoryRangeMap]]
    ): Long = {
      def findMinLocForRangesAux(
          ranges: List[(Long, Long)],
          currMin: Long,
          currSeed: Long,
          currRange: (Long, Long)
      ): Long = {
        if (ranges.isEmpty && !inRange(currSeed, currRange)) currMin
        else {
          if (inRange(currSeed, currRange)) {
            val nextVal =
              Math.min(currMin, AlmanacInfo.fromSeed(currSeed, maps).loc)
            findMinLocForRangesAux(ranges, nextVal, currSeed + 1, currRange)
          } else
            findMinLocForRangesAux(
              ranges.tail,
              currMin,
              ranges.head._1,
              ranges.head
            )
        }
      }

      findMinLocForRangesAux(
        ranges.tail,
        Long.MaxValue,
        ranges.head._1,
        ranges.head
      )
    }

    (parsedInfo.findMinLocation, findMinLocForRanges(newSeeds, parsedInfo.maps))
