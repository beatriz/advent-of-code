let fs = require('fs')

export function readLines(fileNum: number | undefined = undefined): string[] {
  return fs.readFileSync(fileNum ? `./input/day${fileNum}.txt` : `./input/test.txt`, "utf8").split("\n")
}
