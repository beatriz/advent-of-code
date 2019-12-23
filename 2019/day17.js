let fs = require('fs')
let _ = require('lodash')
let Intcode = require('./intcode')

const x = fs.readFileSync('./input/day17.txt', "utf8");
const nums = x.split(',').map(v => Number(v))

const output = Intcode.run(nums, 0)
const a = output.map(x => String.fromCharCode(x))
const length = _.findIndex(a, x => x === '\n') + 1;
let sumOfIntersections = 0;
let chunks = _.chunk(a, length)
for (i = 1; i < chunks.length - 1; i ++) {
  for (j = 1; j < chunks[i].length - 1; j++ ){
    if (chunks[i][j] === '#' && chunks[i-1][j] === '#' && chunks[i][j-1] === '#' && chunks[i+1][j] === '#' && chunks[i][j+1] === '#'){
      chunks[i][j] = 'O';
      sumOfIntersections += i * j;
    }
  }
}

console.log(_.flatten(chunks).join(''))
console.log('Part 1:', sumOfIntersections)
