import { readLines } from "./util"
import _ from "lodash"

interface Coords {
  w: number,
  x: number,
  y: number,
  z: number
}

const val = {
  w: 0,
  x: 0,
  y: 0,
  z: 0
}

const lines = readLines(24)

interface Instruction {
  variables: (c: Coords) => Coords
}

const getValue = (value: string, c: Coords) => {
  const num = parseInt(value) as number
  return isNaN(num) ? c[value as "w"|"x"|"y"|"z"] : num
}

const instructions: ((c: Coords, inp: number[]) => [Coords, number[]])[] = lines.map(l => {
  const splitLine = l.split(' ')
  const variable = splitLine[1] as "w"|"x"|"y"|"z"
  const value = splitLine[2]
  switch (splitLine[0]) {
    case "add":
      return (c: Coords, inp: number[]) => {
        return [{...c, [variable]: c[variable] + getValue(value, c)}, inp]
      }
    case "mul":
      return (c: Coords, inp: number[]) => {
        return [{...c, [variable]: c[variable] * getValue(value, c)}, inp]
      }
    case "div":
      return (c: Coords, inp: number[]) => {
        return [{...c, [variable]: Math.floor(c[variable] / getValue(value, c))}, inp]
      }
    case "mod":
      return (c: Coords, inp: number[]) => {
        return [{...c, [variable]: c[variable] % getValue(value, c)}, inp]
      }
    case "eql":
      return (c: Coords, inp: number[]) => {
        return [{...c, [variable]: c[variable] === getValue(value, c) ? 1 : 0}, inp]
      }
    case "inp":
      return (c: Coords, inp: number[]) => [{...c, [variable]: inp[0]}, _.tail(inp)]
    default:
      return (c: Coords, inp: number[]) => [c, inp]
  }
})

const runProgram = (value: number) => {
  const valueArr = Array.from(String(value), Number)
  const final = instructions.reduce<[Coords, number[]]>((acc, inst) => inst(acc[0], acc[1]), [val, valueArr])
  return final[0]
}

const findLargestValid = () => {
  let isValid = false
  let currInput = 99999999999999
  
  while (!isValid) {
    if (currInput.toString().indexOf('0') !== -1) {
      currInput--
      continue
    }

    console.log(currInput)

    const res = runProgram(currInput)
    if (res.z === 0) {
      isValid = true
    }

    currInput--
  }

  return currInput
}

const solve = (backwards: boolean) => {
  let isValid = false
  let input = backwards ? 99999999999999 : 11111111111111
  const delta = backwards ? -1 : 1

  while (!isValid) {
    if (input.toString().indexOf('0') !== -1) {
      input += delta
      continue
    }

    const res = runProgram(input)
    if (res.z === 0) {
      isValid = true
    }

    input += delta
  }

  return input
}


console.log(findLargestValid())
console.log("Part 1: " + solve(true))
console.log("Part 2: " + solve(false))

