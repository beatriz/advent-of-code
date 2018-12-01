#!/usr/bin/env ruby

line = ""
numbers = Array.new
loop do
    line = gets.chomp
    break if line == "exit"
    numbers.push(line.to_i)    
end

offset = 0
steps = 0
while offset < numbers.length
    prev_offset = offset
    jump = numbers[offset]
    offset = offset + jump
    numbers[prev_offset] = numbers[prev_offset] + 1

    steps += 1
end

puts steps