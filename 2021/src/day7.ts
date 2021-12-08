import {readLines} from './util'
import _ from "lodash"

interface Result {
  consumption?: number
  value: number
}

const positions = readLines(7)[0].split(',').map(x => parseInt(x))

const possibilities = _.range(_.min(positions) as number, _.max(positions))

function findMinimumConsumption(calculateConsumption: (init: number, end: number) => number) {
  return possibilities.reduce<Result>((acc, curr) => {
    const cons = _.sum(positions.map(p => calculateConsumption(p, curr)))
    if (!acc.consumption || cons < acc.consumption) {
      return {consumption: cons, value: curr}
    }
  
    return acc
  }, {consumption: undefined, value: -1}).consumption
}

console.log("Part 1: " + findMinimumConsumption((init, end) => Math.abs(init - end)))
console.log("Part 2: " + findMinimumConsumption((init, end) => {
  const f = (num: number) => _.range(1, num + 1).reduce((acc, curr) => acc + curr, 0)
  return f(Math.abs(init-end))
}))