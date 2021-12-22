import { readLines } from "./util"

let currDie = 100
function rollDie(): number {
  if (currDie === 100){
    currDie = 1
  } else {
    currDie += 1
  }

  return currDie
}

interface PlayerStatus {
  position: number
  score: number
}

const initPlayers: PlayerStatus[] = readLines(21).map(l => ({position: parseInt(l.split(': ')[1]), score: 0}))

function move(currPlace: number, nSpots: number) {
  const r = (currPlace + nSpots) % 10
  if (r === 0) {
    return 10
  }

  return r
}

function playGame(p: PlayerStatus[], dieRolls: number, currP: number): [PlayerStatus[], number] {
  if (p.find(x => x.score >= 1000)) {
    return [p, dieRolls]
  }

  const dieResult = rollDie() + rollDie() + rollDie()
  const moveResult = move(p[currP].position, dieResult)
  const nextP = p.map((v, i) => i === currP ? {position: moveResult, score: v.score + moveResult} : v)
  return playGame(nextP, dieRolls + 3, currP === 0 ? 1 : 0)
}

const game1 = playGame(initPlayers, 0, 0)
console.log("Part 1: " + (game1[0].find(p => p.score < 1000) as PlayerStatus).score * game1[1])