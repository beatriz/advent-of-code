let fs = require('fs')
let _ = require('lodash')
const width = 25;
const height = 6;

const input = fs.readFileSync('./input/day8.txt', "utf8").split("\n")[0];

const numLayers = input.length / (width * height);
let layers = [];

_.range(numLayers).map(i =>{
  let l = [];
  for (j = 0; j < height; j++){
    const start = (i * width * height) + (j * width)
    l.push(input.substr(start, width))
  }
  layers.push(l)
});

const zeroCount = layers.map(l => {
  const ll = _.join(l, '')
  return {layer: ll, zeros: _.countBy(ll)['0']}
});

const minZeros = _.minBy(zeroCount, 'zeros').layer;

const ones = _.countBy(minZeros)['1'];
const twos = _.countBy(minZeros)['2'];

const chooseColor = (current, next) => {
  if (next === '2'){
    return current;
  }
  return next;
}

let image = Array(height).fill(0).map(x => Array(width).fill('2'))
_.reverse(layers).map(layer => {
  for (i = 0; i < height; i++){
    for (j=0; j < width; j++){
      const curr = image[i][j];
      image[i][j] = chooseColor(curr, layer[i][j])
    }
  }
})
console.log("Part 1 " + ones * twos);
console.log("Part 2")
image.forEach(l => console.log(_.join(l, ' ')))
