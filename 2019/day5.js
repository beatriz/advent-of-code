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

const work = (nums, input, offset = 0) => {
  const inst = getInstructions(nums[offset]);

  const getVal = (n) => {
    const pos = nums[offset + n];
    return inst[`param${n}`] === 1 ? pos : nums[pos]
  }

  if (inst.opcode === 99){
    return nums;
  }
  
  let advance = 0;
  
  if (inst.opcode === 1 || inst.opcode === 2){
    const val1 = getVal(1)
    const val2 = getVal(2)
    
    const pos3 = nums[offset + 3];
    nums[pos3] = inst.opcode === 1 ? val1 + val2 : val1 * val2;
    advance = 4;
  }
  else if (inst.opcode === 3){
    const pos = nums[offset + 1];
    nums[pos] = input;
    advance = 2;
  }
  else if (inst.opcode === 4) {
    const pos = nums[offset + 1]
    console.log(nums[pos])
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
    const pos3 = nums[offset + 3];
    nums[pos3] = + (inst.opcode == 7 && val1 < val2) || (inst.opcode == 8 && val1 == val2)
    advance = 4
  }
  
  return shouldEnd(nums, offset + advance) ? nums : work(nums, input, offset + advance);
}

const x = fs.readFileSync('./input/day5.txt', "utf8");
const nums = x.split(',').map(v => Number(v))
console.log('Part 1')
work(nums.slice(), 1)
console.log('Part2')
work(nums, 5)