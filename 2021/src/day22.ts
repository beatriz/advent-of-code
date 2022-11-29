import _ from "lodash"
import { readLines } from "./util"

interface Instruction {
  action: "on" | "off",
  x: [number, number],
  y: [number, number],
  z: [number, number]
}

const readLine = (line: string): Instruction => {
  const action = line.split(' ')[0] as ("on" | "off")
  const cuboids = line.split(' ')[1].split(',')
  const parseCuboid = (cuboidStr: string): [number, number] => {
    const coords = cuboidStr.substring(2).split('..')
    return [parseInt(coords[0]), parseInt(coords[1])]
  }

  return {
    action,
    x: parseCuboid(cuboids[0]),
    y: parseCuboid(cuboids[1]),
    z: parseCuboid(cuboids[2]),
  }
}

const instructions = readLines().map(l => readLine(l))

const max = 50
const initArray: number[][][] = new Array(max * 2 + 1).fill(0).map(x => new Array(max * 2 + 1).fill(0).map(y => new Array(max * 2 + 1).fill(0)))
const isOutOfBounds = (v: [number, number]) => {
  return v[0] > max || v[1] < (max * -1)
}
const part1 = instructions.reduce((array, instruction) => {
  const value = instruction.action === 'on' ? 1 : 0
  if (isOutOfBounds(instruction.z) || isOutOfBounds(instruction.y) || isOutOfBounds(instruction.x)) {
    console.log("ignoring out of bounds instruction", instruction)
    return array
  }
  let newArr = _.cloneDeep(array)
  _.range(instruction.z[0], instruction.z[1] + 1).map(z => {
    _.range(instruction.y[0], instruction.y[1] + 1).map(y => {
      _.range(instruction.x[0], instruction.x[1] + 1).map(x => {
        if (array[z] !== undefined && array[z][y] !== undefined && array[z][y][x] !== undefined) {
          newArr[z][y][x] = value
        }
      })
    })
  })

  return newArr
}, initArray)


console.log("Part 1: " + _.filter(_.flattenDeep(part1), x => x === 1).length)