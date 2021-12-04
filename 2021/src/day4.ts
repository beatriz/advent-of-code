import { readLines } from "./util";
import _ from "lodash"


interface BingoCell {
  value: number
  isChecked: boolean
  x: number
  y: number
}

class BingoBoard {
  cells: BingoCell[]
  id: number
  finalNumber?: number

  constructor(c: BingoCell[], i: number) {
    this.cells = c
    this.id = i
  }

  checkNumber(v: number) {
    return new BingoBoard(this.cells.map(c => c.value === v ? {value: c.value, isChecked: true, x: c.x, y: c.y} : c), this.id)
  }

  isWinner(): boolean {
    const checkedCells = this.cells.filter(c => c.isChecked)
    return _.has(_.invert(_.countBy(checkedCells, c => c.x)), '5') || _.has(_.invert(_.countBy(checkedCells, c => c.y)), '5')
  }

  setFinalNumber(finalN: number) {
    this.finalNumber = finalN
    return this
  }

  finalScore() {
    const unmarked = _.sumBy(this.cells.filter(c => !c.isChecked), 'value')
    return unmarked * (this.finalNumber || 0)
  }
}

const lines = readLines("day4")
const numbers = lines[0].split(',').map(x => parseInt(x))

const boardsInput = lines.slice(2).filter(x => x.length !== 0)
const boards = new Array(boardsInput.length / 5)
  .fill('')
  .map((_, i) => boardsInput.slice(i * 5, (i + 1) * 5))
  .map((b, bi) => {
    return b.reduce<BingoBoard>((acc, line, j) => {
      return new BingoBoard(acc.cells.concat(line.split(' ').filter(x => x.length !== 0).map((x, i) => ({value: parseInt(x), isChecked: false, x: i, y: j}))), bi)
    }, new BingoBoard([], 0))
  })

const [b, winners] = numbers.reduce<[BingoBoard[], BingoBoard[]]>((acc, n) => {
  const [currBoards, currWinners] = acc
  const newBoards = currBoards.map(b => b.checkNumber(n))
  const newWinners = newBoards.filter(b => b.isWinner())
  
  return [newBoards.filter(b => !newWinners.find(w => w.id === b.id)), currWinners.concat(newWinners.map(w => w.setFinalNumber(n)))]
}, [boards, []])

console.log("Part 1: " + winners[0].finalScore())
console.log("Part 2: " + winners[winners.length - 1].finalScore())

