let _ = require('lodash')

const min = 246540;
const max = 787419;

const checkPass = (pass, additional = false) => {
  const passArr = ('' + pass).split('');
  let double = false;
  let previous = '';
  for (const c of passArr){
    if (c === previous) {
      double = true;
    }
    else if (previous > c){
      return false;
    }
    previous = c;
  }
  return double && (additional ? additionalCheck(passArr) : true);
}

const additionalCheck = passArr => {
  return _(passArr).countBy().pickBy(x => x == 2).some()
}

const part1 = () => {
  return _(_.range(min, max + 1)).map(v => checkPass(v)).compact().size()
}

const part2 = () => {
  return _(_.range(min, max + 1)).map(v => checkPass(v,true)).compact().size()
}



console.log("Part 1: " + part1())
console.log("Part 2: " + part2())
