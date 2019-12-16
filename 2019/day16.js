let _ = require('lodash')
let fs = require('fs')

const basePattern = [0, 1, 0, -1];
const nPhases = 100;


const runPhase = input => {
  return input.map((inp, i) => {
    let pattern = [];
    for (j = 0; pattern.length < input.length; j++){
      for (r = 0; r < i + 1; r ++){
        if (j === 0 && r === 0) { continue; }
        if (pattern.length >= input.length) {break;}
        const index = j % 4;
        pattern.push(basePattern[index])
      }
    }
    const v = _.zip(input, pattern).map(([a,b]) => a * b).reduce((a, b) => a + b)
    return Math.abs(v) % 10
  });
};


const runAll = (initialInput) => {
  let val = initialInput;
  for (i = 0; i < nPhases; i++) {
    val = runPhase(val);
  }
  return val.join('');
}

const x = fs.readFileSync('./input/day16.txt', "utf8");

const nums1 = x.split('').map(v => Number(v))
// console.log("Part 1:", runAll(nums).substr(0, 8))

const nums2 = _.repeat('03036732577212944063491565474664', 10000).split('').map(v => Number(v)); // TODO
const offset = Number(x.substr(0, 7))
console.log('Part 2: ', runAll(nums2).substr(offset - 1, 8))

