#!/usr/bin/env ruby

def nearest_odd_square(x)
    res = 1
    while(x > res ** 2)
        res += 2
    end
    return res
end

input = ARGV[0].to_i
# bottom right corner are the odd squares
odd_num = nearest_odd_square(input)

i = odd_num ** 2
turns = 0
moves = 0
# each arm is the length of the sqrt of the odd square on the corner
# form there just find the coordinates and calculate the manhattan distance
while (i > input)
    moves = 0
    while (moves < odd_num - 1)
        i -= 1
        moves += 1
        if (i == input)
        break
        end
    end
    if (i != input)
        turns += 1
    end
end

#since we calculate the module, it doesn't matter which is x or y
x = odd_num / 2
y = (x - moves).abs
result = y + x
puts result
