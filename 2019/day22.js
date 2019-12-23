const _ = require('lodash')

const dealIntoNewStack = (deck) =>  {return _.reverse(deck)}

const cutN = (deck, n) => {
  const head = _.take(deck, n >= 0 ? n : deck.length - Math.abs(n))
  const tail = _.takeRight(deck, n >= 0 ? deck.length - n : Math.abs(n))
  return _.concat(tail, head)
}

const dealWithIncrement = (deck, n) => {
  const shuffled = [];
  let currentPos = 0;
  for (i = 0; i < deck.length; i++) {
    shuffled[currentPos] = deck[i];
    if ((currentPos + n) >= deck.length){
      const nextPos = (currentPos + n) - deck.length
      currentPos = nextPos;
    } else {
      currentPos += n;
    }
  }
  return shuffled;
}

const fs = require('fs')

const lines = fs.readFileSync('./input/day22.txt', "utf8").split("\n");
let deck = _.range(10007)

_.forEach(lines, l => {
  if (l === 'deal into new stack') {
    deck = dealIntoNewStack(deck);
  } else if (_.startsWith(l, 'deal with increment')) {
    const n = Number(l.substr('deal with increment'.length))
    deck = dealWithIncrement(deck, n)
  } else if (_.startsWith(l, 'cut')){
    const n = Number(l.substr('cut'.length))
    deck = cutN(deck, n)
  }
})

console.log('Part 1:', _.indexOf(deck, 2019))
