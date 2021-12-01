let fs = require('fs')

export function readLines(file: string): any[] {
  return fs.readFileSync(`./input/${file}.txt`, "utf8").split("\n")
}
