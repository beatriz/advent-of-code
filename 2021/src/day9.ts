import _ from "lodash";
import { readLines } from "./util";

const matrix = readLines(9).map(l => l.split('').map(x => parseInt(x)))

function get(arr: number[][], j: number, i: number): number {
  return (arr[j] === undefined || arr[j][i] === undefined) ? 9 : arr[j][i]
}

let lowPoints: number[] = []
for (let j = 0; j < matrix.length; j++) {
  const line = matrix[j]
  for (let i = 0; i < line.length; i++) {
    const around = []
    around.push(get(matrix, j, i-1))
    around.push(get(matrix, j, i+1))
    around.push(get(matrix, j-1, i))
    around.push(get(matrix, j+1, i))

    if (_.min(around) as number > matrix[j][i]) {
      lowPoints.push(matrix[j][i])
    }
  }
}

console.log("Part 1: " + _.sum(lowPoints.map(p => p + 1)))