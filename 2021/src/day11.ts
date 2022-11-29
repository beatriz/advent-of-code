import _ from "lodash";
import { readLines } from "./util";

interface Coordinate {
  x: number
  y: number
}

const maxX = 9
const maxY = 9

const matrix = readLines().map(l => l.split('').map(x => parseInt(x)))

function increaseAllNotFlashing(matrix: number[][]): number[][] {
  return matrix.map(line => line.map(o => o < 9 ? o + 1 : o))
}

function flash(matrix: number[][], point: Coordinate) {
  const {x, y} = point
  const xs = x === 0 ? [0, 1] : x === maxX ? [x-1, x] : [x-1, x, x+1]
  const ys = y === 0 ? [0, 1] : y === maxY ? [y-1, y] : [y-1, y, y+1]
  console.log(xs, ys)
  _.zip(xs, ys).forEach(c => matrix[y][x] += 1)
  return matrix
}

function print(matrix: number[][]){
  matrix.forEach(l => console.log(l.join('')))
}

const m = `
11111
19991
19191
19991
11111
`.split('\n').map(l => l.split('').map(x => parseInt(x)))

// print(increaseAllNotFlashing(m))
print(flash(m, {x: 1, y: 1}))

