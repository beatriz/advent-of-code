#!/usr/bin/env ruby

checksum = 0
line = ""
while line != 'exit'
    line = gets.chomp
    numbers = line.split.map(&:to_i).sort{ |x,y| y <=> x }
    for i in 0...numbers.length
        for j in (i+1)...numbers.length
            if numbers[i] % numbers[j] == 0
                checksum += (numbers[i] / numbers[j])
                break
            end
        end
    end
end

puts checksum