import { readLines } from "./util";

const lines = readLines("day3")

function getMostCommonBit(bits: string[]): string | undefined {
  const zero = bits.filter(x => x === '0').length
  const one = bits.length - zero
  if (zero === one) {
    return undefined
  } else if (zero > one) {
    return '0'
  } else {
    return '1'
  }
}

function getOtherBit(bit: string) {
  return bit === '0' ? '1' : '0'
}

const [gamma, epsilon] = lines[0].split('').reduce((acc, _, i) => {
  const bits = lines.map(x => x[i])
  const mostCommon = getMostCommonBit(bits) || '1'
  return [acc[0] + mostCommon, acc[1] + getOtherBit(mostCommon)]
}, ['', ''])

const [ogr, sr] = lines[0].split('').reduce((acc, _, i) => {
  function removeElements(arr: string[], keepMostCommon: boolean) {
    if (arr.length === 1) {
      return arr
    }
  
    const mostCommon = getMostCommonBit(arr.map(x => x[i]))
    const toKeep = keepMostCommon ? (mostCommon || '1') : (mostCommon ? getOtherBit(mostCommon) : '0')
    return arr.filter(x => x[i] === toKeep)
  }

  const [currOgr, currSr] = acc
  return [removeElements(currOgr, true), removeElements(currSr, false)]
}, [lines, lines])

console.log("Part 1: " + parseInt(gamma, 2) * parseInt(epsilon, 2))
console.log("Part 2: " + parseInt(ogr[0], 2) * parseInt(sr[0], 2))