import _ from "lodash"
import { readLines } from "./util"

interface Result {
  incompleteChars: string[]
  expectedLastChar?: string
}

const errorScores: Record<string, number> = {
  ')': 3,
  ']': 57,
  '}': 1197,
  '>': 25137
}

const expectedLastMap: Record<string, string> = {
  ')': '(',
  ']': '[',
  '}': '{',
  '>': '<'
}
const toCompleteMap: Record<string, string> = {
  '(': ')',
  '[': ']',
  '{': '}',
  '<': '>'
}

const toCompleteScores: Record<string, number> = {
  ')': 1,
  ']': 2,
  '}': 3,
  '>': 4 
}

function validateLine(line: string): string[] | string {
  const chars: string[] = []

  for (const char of line) {
    let expectedLast: string | undefined = undefined
    switch (char) {
      case "(":
      case "[":
      case "{":
      case "<":
        chars.push(char)
        break
      case ')':
      case ']':
      case '}':
      case '>':
        expectedLast = expectedLastMap[char]
        break
    }

    if (expectedLast === undefined) {
      continue
    }

    if (_.last(chars) === expectedLast) {
      chars.pop()
    } else {
      return char
    }
  }

  return chars
}

const lines = readLines(10)
const res = lines.reduce((acc, currLine) => {
  const res = validateLine(currLine)
  if (typeof res === "string") {
    return acc + errorScores[res]
  }
  return acc
}, 0)

const incompleteLines = lines.map(l => validateLine(l)).filter(r => _.isArray(r)).map(x => x as string[])
const incompleteScores = incompleteLines.map(l => {
  const x = _.reverse(l).map(c => toCompleteMap[c])
  const y =  x.reduce((accScore, c) => {
    const score = toCompleteScores[c]
    return accScore * 5 + score
  }, 0)
  console.log(x, y)
  return y
})
const x = _.sortBy(incompleteScores)

console.log("Part 1: " + res)
console.log("Part 2: " + x[Math.floor(x.length / 2)])