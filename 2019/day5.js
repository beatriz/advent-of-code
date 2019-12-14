let fs = require('fs')
let Intcode = require('./intcode')

const x = fs.readFileSync('./input/day5.txt', "utf8");
const nums = x.split(',').map(v => Number(v))
console.log('Part 1')
const res1 = Intcode.run(nums.slice(), 1)
console.log(res1[res1.length - 1])
console.log('Part2')
const res2 = Intcode.run(nums.slice(), 5)
console.log(res2[res2.length - 1])
