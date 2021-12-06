import { readLines } from "./util";
import _ from "lodash"

interface Coordinate {
  x: number
  y: number
}

class Line {
  start: Coordinate
  end: Coordinate

  constructor(x1: number, y1: number, x2: number, y2: number) {
    this.start = {x: x1, y: y1}
    this.end = {x: x2, y: y2}
  }

  yDifference() {
    return _.max([this.start.y, this.end.y]) as number - (_.min([this.start.y, this.end.y]) as number)
  }

  xDifference() {
    return _.max([this.start.x, this.end.x]) as number - (_.min([this.start.x, this.end.x]) as number)
  }

  getXs() {
    if (this.start.x === this.end.x) {
      return []
    }
    const end = this.start.x > this.end.x ? this.end.x - 1 : this.end.x + 1
    return _.range(this.start.x, end)
  }

  getYs(): number[] {
    if (this.start.y === this.end.y) {
      return []
    }
    const end = this.start.y > this.end.y ? this.end.y - 1 : this.end.y + 1
    return _.range(this.start.y, end)
  }
}

const input = readLines(5)

const lines = input.map(i => {
  const s = i.substring(0, i.indexOf('-') - 1).split(',')
  const e = i.substring(i.indexOf('>') + 2).split(',')
  return new Line(parseInt(s[0]), parseInt(s[1]), parseInt(e[0]), parseInt(e[1]))
})

const boardSize = lines.reduce<[Coordinate, Coordinate]>((acc, curr): [Coordinate, Coordinate] => {
  const [min, max] = acc
  const newMin = {x: _.min([min.x, curr.start.x, curr.end.x]) as number, y: _.min([min.y, curr.start.y, curr.end.y]) as number}
  const newMax = {x: _.max([max.x, curr.start.x, curr.end.x]) as number, y: _.max([max.y, curr.start.y, curr.end.y]) as number}

  return [newMin, newMax]
}, [{x: 1000, y: 1000}, {x: 0, y: 0}])


function run(withHorizontals: boolean) {
  let board: number[][] = new Array(boardSize[1].y + 1).fill(0).map(x => new Array(boardSize[1].x + 1).fill(0))
  
  lines.forEach(l => {
    if (l.start.x === l.end.x) {
      l.getYs().forEach(y => {
        board[y][l.start.x] = board[y][l.start.x] + 1
      })
    }

    if (l.start.y === l.end.y) {
      l.getXs().forEach(x => {
        board[l.start.y][x] = board[l.start.y][x] + 1
      })
    }

    if (withHorizontals && l.xDifference() === l.yDifference()) {
      const ys = l.getYs()
      l.getXs().forEach((x, i) => {
        board[ys[i]][x] += 1
      })
    }
  })

  return _.filter(_.flatten(board), x => x > 1).length
}

console.log("Part 1: " + run(false))
console.log("Part 2: " + run(true))