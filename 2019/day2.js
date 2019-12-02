let fs = require('fs');

const shouldEnd = (arr, to) => to > arr.length - 1

const work = (nums, offset = 0) => {
  const zero = nums[offset];
  if (zero == 99) {
    return nums;
  }
  
  const pos1 = nums[offset + 1];
  const pos2 = nums[offset + 2];
  const pos3 = nums[offset + 3];
  
  const val1 = nums[pos1];
  const val2 = nums[pos2];
  
  nums[pos3] = zero === 1 ? val1 + val2 : val1 * val2;
  return shouldEnd(nums, offset + 4) ? nums : work(nums, offset + 4);
}

const findOutput = (ns, v1 = 12, v2 = 2) => {
  ns[1] = v1;
  ns[2] = v2;
  return work(ns)[0];
}

const findValues = (ns, result = 19690720) => {
  const max = ns.length;
  for (v1 = 0; v1 < max; v1++) {
    for (v2 = 0; v2 < max; v2++){
      const arr = ns.slice();
      const output = findOutput(arr, v1, v2);
      if (output === result){
        return 100 * v1 + v2;
      }
    }
  }
  return 0;
}

const input = fs.readFileSync('./input/day2.txt', "utf8");
const nums = input.split(',').map(x => Number(x));
console.log("Part 1: " + findOutput(nums.slice()));
console.log("Part 2: " + findValues(nums))
