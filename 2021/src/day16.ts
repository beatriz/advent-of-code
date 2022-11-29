import _ from "lodash"
const hexBin: Record<string, string> = {
  '0': '0000',
  '1': '0001',
  '2': '0010',
  '3': '0011',
  '4': '0100',
  '5': '0101',
  '6': '0110',
  '7': '0111',
  '8': '1000',
  '9': '1001',
  'A': '1010',
  'B': '1011',
  'C': '1100',
  'D': '1101',
  'E': '1110',
  'F': '1111',
}

const hexToBin = (hexStr: string) => hexStr.split('').map(c => hexBin[c]).join('')

const getBits = (bitStr: string, nBits: number, startAt: number = 0) => {
  const bitLenght = 4
  return bitStr.substring(startAt * bitLenght, (startAt + nBits + 1) * bitLenght)
}

const binStr = hexToBin("38006F45291200")

const packetVersion = parseInt(binStr.substring(0, 3), 2)
const packetTypeId = parseInt(binStr.substring(3, 6), 2)

// let a: [string[], boolean] = [[], false]
// switch(packetTypeId) {
//   case 4:
//     console.log(binStr)
//     a = _.chunk(binStr.substring(6), 5).reduce<[string[], boolean]>((acc, curr) => {
//       const [numbers, ended] = acc
//       if (ended) { return acc }

//       const end = curr[0] === '0'
//       return [numbers.concat(curr.join('').substring(1)), end]

//     }, [[], false])
//   default:
//     const lengthTypeId = binStr[6]
//     if (lengthTypeId === '0') {
//       const nSubpackets = parseInt(binStr.substring(7, 7 + 15), 2)
//       console.log(nSubpackets)
//     }
// }

function readBits(bits: string) {
  const packetVersion = parseInt(bits.substring(0, 3), 2)
  const packetTypeId = parseInt(bits.substring(3, 6), 2)
  console.log(packetTypeId)
  console.log(packetTypeId)
  if (packetTypeId === 4) {
    const res = readLiteralValue(bits.substring(6))
    return [res, `${bits.substring(0,6)}${res}`]
  } else {
    const lengthTypeId = bits[6]
    if (lengthTypeId === '0') {
      const nSubpackets = parseInt(bits.substring(7, 7 + 15), 2)
      console.log(nSubpackets)
      let length = 0
      let results = []
      while (length < nSubpackets) {
        const [res, totalBits] = readBits(bits.substring(7 + 15 + length))
        length += totalBits.length
        results.push(res)
      }
      return [results]
    }
  }

}

function readLiteralValue(bits: string) {
  let readBits = []
  const [res,] = _.chunk(bits, 5).reduce<[string[], boolean]>((acc, curr) => {
    const [numbers, ended] = acc
    if (ended) { return acc }

    const end = curr[0] === '0'
    return [numbers.concat(curr.join('').substring(1)), end]

  }, [[], false])

  return res.join('')
}

// readBits(hexToBin("38006F45291200"))
// console.log("A", readLiteralValue("01010000001"))
console.log("B", readBits("01010010001001000000000"))