import {readLines} from "./util"

interface Instruction {
  direction: Direction
  units: number
}

type Direction = "forward" | "down" | "up"

class Location1 {
  horizontal: number
  depth: number

  constructor(h: number, d: number) {
    this.horizontal = h
    this.depth = d
  }

  move(inst: Instruction): Location1 {
    switch (inst.direction) {
      case "forward":
        return new Location1(this.horizontal + inst.units, this.depth)
      case "down":
        return new Location1(this.horizontal, this.depth + inst.units)
      case "up":
        return new Location1(this.horizontal, this.depth - inst.units)
    }
  }
}

class Location2 {
  horizontal: number
  depth: number
  aim: number

  constructor(h: number, d: number, a: number) {
    this.horizontal = h
    this.depth = d
    this.aim = a
  }

  move(inst: Instruction): Location2 {
    switch (inst.direction) {
      case "forward":
        return new Location2(this.horizontal + inst.units, this.depth + (this.aim * inst.units), this.aim)
      case "down":
        return new Location2(this.horizontal, this.depth, this.aim + inst.units)
      case "up":
        return new Location2(this.horizontal, this.depth, this.aim - inst.units)
    }
  }
}

const instructions: Instruction[] = readLines("day2").map(x => {
  const arr = x.split(" ")
  if (arr[0] !== "forward" && arr[0] !== "down" && arr[0] !== "up") {
    throw "Unexpected direction"
  }

  return {
    direction: arr[0],
    units: parseInt(arr[1])
  }
})

function part1() {
  const finalLocation = instructions.reduce<Location1>((acc, curr) => {
    return acc.move(curr)
  }, new Location1(0, 0))

  return finalLocation.depth * finalLocation.horizontal
}

function part2() {
  const finalLocation = instructions.reduce<Location2>((acc, curr) => {
    return acc.move(curr)
  }, new Location2(0, 0, 0))

  return finalLocation.depth * finalLocation.horizontal
}

console.log("Part 1: " + part1())
console.log("Part 2: " + part2())