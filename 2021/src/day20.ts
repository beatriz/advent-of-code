import { readLines } from "./util";
import _, { add } from "lodash"

const lines = readLines(20)
const enhancementAlgo = lines[0]


function get2(arr: number[][], y: number, x: number): number {
  return (arr[y] === undefined || arr[y][x] === undefined) ? 0 : arr[y][x]
}

function get(arr: string[][], y: number, x: number): string {
  return (arr[y] === undefined || arr[y][x] === undefined) ? '.' : arr[y][x]
}

const getOutputPixel2 = (matrix: number[][], x: number, y: number) => {
  const str = _.concat(
    get2(matrix, y-1, x-1), get2(matrix, y-1, x), get2(matrix, y-1, x+1),
    get2(matrix, y, x-1), get2(matrix, y, x), get2(matrix, y, x+1),
    get2(matrix, y+1, x-1), get2(matrix, y+1, x), get2(matrix, y+1, x+1)).join('')

  const enhanceIndex = parseInt(str, 2)
  return enhancementAlgo[enhanceIndex]
}

const getOutputPixel = (matrix: string[][], x: number, y: number) => {
  const str = _.concat(
    get(matrix, y-1, x-1), get(matrix, y-1, x), get(matrix, y-1, x+1),
    get(matrix, y, x-1), get(matrix, y, x), get(matrix, y, x+1),
    get(matrix, y+1, x-1), get(matrix, y+1, x), get(matrix, y+1, x+1)).join('')

  const enhanceIndex = parseInt(str.replace(new RegExp('\\.', 'g'), '0').replace(new RegExp('#', 'g'), '1'), 2)
  return enhancementAlgo[enhanceIndex]
}


const iteration = (matrix: string[][]) => {
  const addColumns = 1
  const newM = new Array(matrix.length + addColumns * 2).fill('.').map(x => new Array(matrix.length + addColumns * 2).fill('.'))
  for (let y = 0; y < newM.length; y++) {
    const line = newM[y]
    for (let x = 0; x < line.length; x++) {
      newM[y][x] = getOutputPixel(matrix, x-addColumns, y-addColumns)
    }
  }

  return newM
}

const iteration2 = (matrix: number[][]) => {
  const addColumns = 1
  const newM = new Array(matrix.length + addColumns * 2).fill(0).map(x => new Array(matrix.length + addColumns * 2).fill(0))
  for (let y = 0; y < newM.length; y++) {
    const line = newM[y]
    for (let x = 0; x < line.length; x++) {
      newM[y][x] = getOutputPixel2(matrix, x-addColumns, y-addColumns)
    }
  }

  return newM
}

const countLitPixels = (matrix: string[][]) => 
  matrix.reduce((acc, line) => acc + line.filter(c => c === '#').length, 0)

const countLitPixels2 = (matrix: number[][]) => 
  matrix.reduce((acc, line) => acc + _.sum(line), 0)

const nIterations = (initMatrix: string[][], n: number) => {
  return _.range(n).reduce(acc => iteration(acc), initMatrix)
}

const nIterations2 = (initMatrix: number[][], n: number) => {
  return _.range(n).reduce(acc => iteration2(acc), initMatrix)
}

const m = lines.slice(2).map(l => l.split(''))
console.log(countLitPixels(nIterations(m, 2)))

const m2 = lines.slice(2).map(l => l.split('').map(c => c === '#' ? 1 : 0))