import _ from "lodash";
import { readLines } from "./util";

const lines = readLines(6)

const lanternFish = lines[0].split(',').map(x => parseInt(x))

// This doesn't work for part 2 as it will go forever and eventually run out of memory
function procriate(nDays: number) {
  const final = _.range(nDays).reduce((acc, curr) => {
    const newFish = acc.filter(x => x === 0).length
    const newAll = acc.map(x => {
      if (x === 0) {
        return 6
      } 

      return x - 1
    })

    return newAll.concat(new Array(newFish).fill(8))
  }, lanternFish)

  return final.length
}

function procriateBetter(nDays: number) {
  const initial = _.fromPairs(_.range(9).map(i => [i, lanternFish.filter(x => x === i).length]))

  const final = _.range(nDays).reduce(acc => {
    return _.fromPairs(_.range(9).map(i => {
      const val = i === 8 ? acc[0] : (i === 6 ? acc[7] + acc[0] : acc[i + 1])
      return [i, val]
    }))
  }, initial)

  return _.sum(_.values(final))
}

console.log("Part 1: " + procriateBetter(80))
console.log("Part 2: " + procriateBetter(256))