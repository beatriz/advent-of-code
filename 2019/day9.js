let fs = require('fs')
let Intcode = require('./intcode')

const x = fs.readFileSync('./input/day9.txt', "utf8");
const nums = x.split(',').map(v => Number(v))
console.log('Part 1')
const res1 = Intcode.run(nums.slice(), 1)
console.log(res1[0])
console.log('Part2')
console.log(Intcode.run(nums.slice(), 2)[0])
