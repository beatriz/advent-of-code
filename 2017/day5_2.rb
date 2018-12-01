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
#puts "step #{steps}: #{numbers}"
while offset < numbers.length
    #puts "offset #{offset}"
    prev_offset = offset
    jump = numbers[offset]
    #puts "jump #{jump}"
    offset = offset + jump
    if (jump >= 3)
        numbers[prev_offset] = numbers[prev_offset] - 1
    else
        numbers[prev_offset] = numbers[prev_offset] + 1
    end

    steps += 1
    #puts "step #{steps}: #{numbers}"
end
#puts "result: #{numbers}"
puts steps