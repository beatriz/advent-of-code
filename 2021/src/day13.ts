import { readLines } from "./util";
import _ from "lodash"

interface Coordinate {
  x: number
  y: number
}

interface FoldInstruction {
  foldLine: number
  foldBy: 'x' | 'y'
}

const lines = readLines(13)
const emptyLine = _.findIndex(lines, x => x.length === 0)
const coordinates = lines.slice(0, emptyLine).map(l => ({x: parseInt(l.split(',')[0]), y: parseInt(l.split(',')[1])}))
const foldInstructions: FoldInstruction[] = lines.slice(emptyLine + 1).map(l => {
  const equalsIndex = l.indexOf('=')
  return {
    foldLine: parseInt(l.slice(equalsIndex + 1)),
    foldBy: l[equalsIndex - 1] as 'x' | 'y'
  }
})

function fold(coords: Coordinate[], inst: FoldInstruction) {
  const toFold = coords.filter(c => c[inst.foldBy] > inst.foldLine)
  const folded = toFold.map(c => {
    _.set(c, inst.foldBy, inst.foldLine - (c[inst.foldBy] - inst.foldLine))
    return c
  })
  return _.uniqWith(coords.filter(c => c[inst.foldBy] < inst.foldLine).concat(folded), _.isEqual)
}

console.log("Part 1: " + fold(coordinates, foldInstructions[0]).length)

const folded = foldInstructions.reduce((acc, curr) => {
  return fold(acc, curr)
}, coordinates)

function print(coords: Coordinate[]) {
  const maxCoord = (c: 'x' | 'y') => _.get(_.maxBy(coords, c), c) as number + 1
  const f = _.range(0, maxCoord('y')).map(y => _.range(0, maxCoord('x')).map(x => _.find(coords, c => c.x === x && c.y === y) ? '#' : '.'))
  f.forEach(l => console.log(l.join('')))
}

console.log("Part 2: " + print(folded))