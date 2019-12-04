let fs = require('fs')
let _ = require('lodash')

const lines = fs.readFileSync("./input/day3.txt", "utf8").split("\n")
const coordinates = lines.map(l => l.split(',').map(c => ({
  direction: c[0],
  steps: Number(c.substr(1))
})));

const actions = {
  'U': { index: 1, sign: 1},
  'D': { index: 1, sign: -1},
  'R': { index: 0, sign: 1},
  'L': { index: 0, sign: -1}
}

const calculateCoords = (initialCoords, a) => {
  const action = actions[a.direction];
  const initialC = initialCoords[action.index];
  return _.range(1, a.steps + 1).map(i => {
    const newC = Array.from(initialCoords);
    newC[action.index] = initialC + (i * action.sign);
    return newC;
  })
}

const part1 = paths => {
  let values = [];
  paths.forEach(p => {
    let coord = [0, 0];
    let thisVal = [];

    p.forEach(c => {
      const newC = calculateCoords(coord, c)
      coord = _.last(newC)
      thisVal.push(newC.map(x => _.join(x, ',')))
    })
    _.uniq(_.flatten(thisVal)).forEach(v => values.push(v))
  })
  return _(values)
  .countBy() // get count by element
  .pickBy(x => x > 1) // filter elements with value (count) > 1
  .keys() //only the keys matter
  .map((v) => {
    const c = v.split(',').map(x => Number(x))
    return Math.abs(c[0]) + Math.abs(c[1])
  }).sortBy().head()
}

const part2 = paths => {
  const p1 = paths[0];
  const p2 = paths[1];
  let coords1 = [0, 0];
  let coords2 = [0, 0];
  let s1 = [];
  let s2 = []
  for (i = 0; i < p1.length; i++) {
    const c1 = calculateCoords(coords1, p1[i])
    const c2 = calculateCoords(coords2, p2[i])
    coords1 = _.last(c1)
    coords2 = _.last(c2)
    c1.map(x => _.join(x, ',')).forEach(x => s1.push(x))
    c2.map(x => _.join(x, ',')).forEach(x => s2.push(x))
    const inter = _.intersection(s1, s2)
    if (!_.isEmpty(inter)){
      return _.indexOf(s1, inter[0]) + _.indexOf(s2, inter[0]) + 2;
    }
  }
}

console.log('Part 1: ' + part1(coordinates))
console.log('Part 2: ' + part2(coordinates))