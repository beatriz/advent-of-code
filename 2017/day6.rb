#!/usr/bin/env ruby

total_blocks = 16

banks = [total_blocks]
found_configurations = Array.new
for i in 0...total_blocks
    banks[i] = ARGV[i].to_i
end

iter = 0
loop do
    index = banks.index(banks.max)
    to_redistribute = banks[index]
    banks[index] = 0
    
    while to_redistribute > 0
        index += 1
        this_index = index - banks.length * (index / banks.length)
        banks[this_index] = banks[this_index] + 1
        to_redistribute -= 1
    end
    iter += 1
    break if found_configurations.include?(banks.to_s)
    found_configurations[iter-1] = banks.to_s
end

puts "iterations #{iter}; size of loop #{iter - (found_configurations.index(banks.to_s) + 1)}"

#10 3 15 10 5 15 5 15 9 2 5 8 5 2 3 6