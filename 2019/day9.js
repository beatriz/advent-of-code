let _ = require('lodash');
let fs = require('fs')

const shouldEnd = (arr, to) => to > arr.length - 1

const getInstructions = (str) => ({
  opcode: str % 100,
  param1: Math.floor(str / 100) % 10 || 0,
  param2: Math.floor(str / 1000) % 10 || 0,
  param3: Math.floor(str / 10000) % 10 || 0,
  size: ('' + str).length
})

const work = (nums, input, relativeBase = 0, offset = 0) => {
  const inst = getInstructions(nums[offset]);
  const getVal = (n) => {
    const pos = nums[offset + n] || 0;
    const notFromPos = n === 3 || inst.opcode === 3;
    switch (inst[`param${n}`]){
      case 0:
        return notFromPos ? pos : nums[pos] || 0;
      case 1:
        return pos || 0;
      case 2:
        return notFromPos ? pos + relativeBase : nums[pos + relativeBase] || 0;
      default:
        console.log('invalid parameter')
        return {end: true};
    }
  }

  if (inst.opcode === 99){
    return {end: true};
  }
  
  let advance = 0;
  let newRelativeBase = relativeBase;
  
  if (inst.opcode === 1 || inst.opcode === 2){
    const val1 = getVal(1)
    const val2 = getVal(2)
    
    const pos3 = getVal(3)
    nums[pos3] = inst.opcode === 1 ? val1 + val2 : val1 * val2;
    advance = 4;
  }
  else if (inst.opcode === 3){
    const pos = getVal(1);
    nums[pos] = input;
    advance = 2;
  }
  else if (inst.opcode === 4) {
    console.log(getVal(1));
    advance = 2;
  }
  else if (inst.opcode <= 6) {
    const val = getVal(1)
    
    if ((inst.opcode == 5 && val != 0) || (inst.opcode == 6 && val == 0)){
      offset = getVal(2)
    } else {
      advance = 3
    }
  } else if (inst.opcode <= 8) {
    const val1 = getVal(1)
    const val2 = getVal(2)
    const pos3 = getVal(3);
    const valBool = (inst.opcode == 7 && val1 < val2) || (inst.opcode == 8 && val1 == val2)
    nums[pos3] = valBool ? 1 : 0  
    advance = 4
  }
  else if (inst.opcode === 9) {
    const val = getVal(1);
    newRelativeBase += val;
    advance = 2;
  }
  
  return { nums, newRelativeBase, newOffset: offset + advance };
}

const run = (n, input) => {
  let relativeBase, offset = 0;
  let output = [];
  let nums = n;
  while (true) {
    const res = work(nums, input, relativeBase, offset);
    if (res.output !== undefined) { output.push(res.output); }
    if (res.end === true) {
      break;
    }
    nums = res.nums;
    relativeBase = res.newRelativeBase;
    offset = res.newOffset;
  }
}

const x = fs.readFileSync('./input/day9.txt', "utf8");
const nums = x.split(',').map(v => Number(v))
console.log('Part 1')
run(nums.slice(), 1)
console.log('Part2')
run(nums.slice(), 2)
