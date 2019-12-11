let fs = require('fs')
let _ = require('lodash')

const lines = fs.readFileSync('./input/day6.txt', "utf8").split("\n");
const input = lines.map(l => l.split(")"));

// console.log(input)
// console.log(_.filter(input, ([a, b]) => a === 'B'))

const getOrbits = center => {
    const x = _.filter(input, ([a, b]) => a === center).map(([a, b]) => b);
    // console.log("getOrbits of", center, "is", x)
    return x;
}

const updateOrbits = (orbits, current) => {
    // console.log(orbits, current)
    const cc = getOrbits(current);
    // console.log(`orbits[${current}] = ${orbits[current]}`)
    // console.log(cc)
    cc.forEach(c => orbits[c] = (orbits[current] || 0) + 1)
    // console.log('getOrbits of', current, 'is', cc)
    return cc;
}

const orbits = {};
let i = 0;
const work = (current) => {
  let next = current.map(c => {
    const a = updateOrbits(orbits, c)
    // console.log("a", a)
    const b = _.flattenDeep(a)
    // console.log("b", b)
    return _.without(b, 'COM')
  })
  // console.log("next", next)

  if (_.isEmpty(next)) {
      return;
  }
  work(_.uniq(_.flatten(next)))
}

work(["COM"])

console.log(orbits)
console.log(_.sum(_.values(orbits)))
