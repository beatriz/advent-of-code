let fs = require('fs')

const calculateBasicRequiredFuel = mass => Math.floor(mass / 3) - 2

const calculateRequiredFuel = mass => {
  const requiredF = calculateBasicRequiredFuel(mass)
  return requiredF + (calculateBasicRequiredFuel(requiredF) < 0 ? 0 : calculateRequiredFuel(requiredF))
}

const lines = fs.readFileSync("./input/day1.txt", "utf8").split("\n")

const run = (fn) => lines.map(l => fn(Number(l))).reduce((a, b) => a + b)

console.log(`Part 1: ${run(calculateBasicRequiredFuel)}`)
console.log(`Part 2: ${run(calculateRequiredFuel)}`)