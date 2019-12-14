let Intcode = require('./intcode')
let fs = require('fs')

const x = fs.readFileSync('./input/day7.txt', "utf8");
const nums = x.split(',').map(v => Number(v))

let maxOutput = 0;

let possibleSettings = [],
    usedChars = [];

function permute(input) {
  var i, ch;
  for (i = 0; i < input.length; i++) {
    ch = input.splice(i, 1)[0];
    usedChars.push(ch);
    if (input.length == 0) {
      possibleSettings.push(usedChars.slice());
    }
    permute(input);
    input.splice(i, 0, ch);
    usedChars.pop();
  }
  return possibleSettings
};

permute([0,1,2,3,4])

function getFinalOutput(phaseSetting, program) {
  let input = 0;
  for (i=0; i < phaseSetting.length; i++){
    const phase = phaseSetting[i];
    const res = Intcode.run(program, [phase, input]);
    input = res[0];
  }
  return input;
}

possibleSettings.forEach(setting => {
  const output = getFinalOutput(setting, nums.slice())
  if (output > maxOutput) {
    maxOutput = output;
  }
})

console.log('Part 1')
console.log(maxOutput)
console.log('=========')

