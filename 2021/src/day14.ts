import { readLines } from "./util";
import _ from 'lodash'

interface InsertionRule {
  pair: string
  element: string
}

const lines = readLines(14)
const initialTemplate = lines[0]
const insertionRules: InsertionRule[] = lines.slice(2).map(l => {
  const [pair, element] = l.split(' -> ')
  return {pair, element}
})

function iteration(initPairs: Record<string, number>, initResult: Record<string, number>) {
  return insertionRules.reduce<[Record<string, number>, Record<string, number>]>((acc, rule) => {
    const [currPairs, currRes] = acc

    const existingPairs = initPairs[rule.pair]
    if (!existingPairs) {
      return [currPairs, currRes]
    }

    const pair1 = `${rule.pair[0]}${rule.element}`
    const pair2 = `${rule.element}${rule.pair[1]}`

    const newPairs = rule.pair === pair1 ?
      {
        ...currPairs,
        [rule.pair]: currPairs[rule.pair],
        [pair2]: (currPairs[pair2] || 0) + existingPairs
      }
      : rule.pair === pair2 ?
      {
        ...currPairs,
        [rule.pair]: currPairs[rule.pair],
        [pair1]: (currPairs[pair1] || 0) + existingPairs
      } :
      {
        ...currPairs,
        [rule.pair]: currPairs[rule.pair] - existingPairs,
        [pair1]: (currPairs[pair1] || 0) + existingPairs,
        [pair2]: (currPairs[pair2] || 0) + existingPairs,
      }

    const newResult = {
      ...currRes,
      [rule.element]: (currRes[rule.element] || 0) + existingPairs,
    }

    return [newPairs, newResult]
  }, [initPairs, initResult])
}

function nSteps(n: number) {
  const initResult = _.mapValues(_.groupBy(initialTemplate.split('')), v => v.length)
  const initPairs = _.mapValues(
    _.groupBy(
      initialTemplate.substring(0, initialTemplate.length - 1).split('').reduce<string[]>((acc, curr, i) => {
      return acc.concat([`${curr}${initialTemplate[i+1]}`])
      }, [])
    ), v => v.length)

  const [, res] = _.range(n).reduce(acc => iteration(acc[0], acc[1]), [initPairs, initResult])
  return (_.max(_.values(res)) as number) - (_.min(_.values(res)) as number)
}

// --- INITIAL IMPLEMENTATION, DOESN'T WORK FOR PART 2 AS IT BUILDS THE STRING
// function insert(init: string) {
//   return init.split('').reduce((acc, curr, i) => {
//     if (i === init.length - 1) {
//       return acc
//     }

//     const pair = `${curr}${init[i+1]}`
//     const insert = (insertionRules.find(r => r.pair === pair) as InsertionRule).element
//     return `${acc}${insert}${init[i+1]}`
//   }, init[0])
// }

// function nSteps(n: number, init: string) {
//   const res = _.range(n).reduce(acc => insert(acc), init)
//   const counts = _.toPairs(_.mapValues(_.groupBy(res.split('')), v => v.length))
//   return (_.maxBy(counts, x => x[1]) as [string, number])[1] - (_.minBy(counts, x => x[1]) as [string, number])[1]
// }

console.log("Part 1: " + nSteps(10))
console.log("Part 2: " + nSteps(40))
