import {readLines} from "./util"

const lines = readLines("day1").map(x => parseInt(x))

function countIncreases(arr: number[]) {
  const [res, _] = arr.reduce<[number, number?]>((acc: [number, number?], curr) => {
    const [count, prev] = acc
    if (prev && curr > prev) {
      return [count + 1, curr]
    }
    return [count, curr]
  }, [0, undefined])

  return res
}

function countIncreases3() {
  const n = lines.reduce((acc: number[], curr: number, i: number) => {
    if (i !== 0 && i !== 1) {
      acc.push(curr + lines[i-1] + lines [i-2])
    }
    
    return acc
  }, [])

  return countIncreases(n)
}

console.log("Part 1: " + countIncreases(lines))
console.log("Part 2: " + countIncreases3())

export {}
