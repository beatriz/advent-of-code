import { readLines } from "./util";
import _, { DictionaryIteratorTypeGuard } from "lodash"

interface Input {
  signalPatterns: string[]
  output: string[]
}

const input: Input[] = readLines(8).map(l => {
  const [s, o] = l.split('|')
  return {
    signalPatterns: _.trim(s).split(' '),
    output: _.trim(o).split(' ')
  }
})

const digitMap = _.toPairs({
  'abcefg': '0',
  'cf': '1',
  'acdeg': '2',
  'acdfg': '3',
  'bcdf': '4',
  'abdfg': '5',
  'abdefg': '6',
  'acf': '7',
  'abcdefg': '8',
  'abcdfg': '9'
})

const getEasy = (nums: string[]) => nums.filter(n => [2, 3, 4, 7].includes(n.length))

const findByCount = (n: [string, number][], count: number) => n.filter(x => x[1] === count).map(x => x[0])

const findConfiguration = (nums: string[]) => {
  const easy = getEasy(nums)
  const easyChars = _.toPairs(_.countBy(_.flatten(easy.map(s => s.split('')))))
  const other = _.difference(nums, easy)
  const otherChars = _.toPairs(_.countBy(_.flatten(other.map(s => s.split('')))))

  const find = (easyCount: number, otherCount: number) => {
    const inters = _.intersection(findByCount(easyChars, easyCount), findByCount(otherChars, otherCount))
    if (inters.length !== 1) {
      throw "ERROR: intersection different than 1 = " + inters
    }

    return inters[0]
  }

  return _.invert({
    a: find(2, 6),
    b: find(2, 4),
    c: find(4, 4),
    d: find(2, 5),
    e: find(1, 3),
    f: find(4, 5),
    g: find(1, 6)
  })
}

const getDigit = (config: _.Dictionary<string>, num: string) => {
  const newNum = num.split('').map(c => config[c])
  const res = digitMap.find(v => v[0].length === newNum.length && _.intersection(v[0].split(''), newNum).length === newNum.length)
  if (res === undefined) {
    throw "ERROR: undefined"
  }
  return res[1]
}

const sum = input.reduce((acc, curr) => {
  const config = findConfiguration(curr.signalPatterns)
  const v = parseInt(curr.output.map(o => getDigit(config, o)).join(''))
  return acc + v
}, 0)

console.log("Part 1: " + _.flatten(input.map(i => getEasy(i.output))).length)
console.log("Part 2: " + sum)