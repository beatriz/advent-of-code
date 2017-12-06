#!/usr/bin/env ruby

checksum = 0
line = ""
while line != 'exit'
    line = gets.chomp
    numbers = line.split.map(&:to_i).sort
    
    checksum += numbers.max - numbers.min
end

puts checksum