let fs = require('fs')
let Intcode = require('./intcode')

const x = fs.readFileSync('./input/day9.txt', "utf8");
const nums = x.split(',').map(v => Number(v))
console.log('Part 1')
Intcode.run(nums.slice(), 1)
console.log('Part2')
Intcode.run(nums.slice(), 2)
