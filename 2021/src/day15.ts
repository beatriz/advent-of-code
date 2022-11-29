import { readLines } from "./util";
import _ from "lodash"

interface Node {
  weight: number
  coords: string
  tentativeDist: number
  neighbours: string[]
}

const lines = readLines()

const nodes: Record<string, Node> = _.fromPairs(_.flatten(lines.map((line, j) => {
  return line.split('').map((c, i) => {
    const is = i === 0 ? [0, 1] : i === line.length - 1 ? [i-1, i] : [i-1, i, i+1]
    const js = j === 0 ? [0, 1] : j === lines.length - 1 ? [j-1, j] : [j-1, j, j+1]
    const neighbours = _.zip(is, js).map(c => `${c[0]},${c[1]}`)

    return [`${i},${j}`, {weight: c, coords: `${i},${j}`, tentativeDist: (i === 0 && j === 0) ? 0 : Infinity, neighbours}]
  })
})))

let current = nodes['0,0']

