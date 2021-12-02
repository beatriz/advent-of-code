let fs = require('fs')

export function readLines(file: string): string[] {
  return fs.readFileSync(`./input/${file}.txt`, "utf8").split("\n")
}
