input = gets.chomp
sum = 0
for i in 0..input.length
    if input[i] == input[(i + input.length / 2) % input.length]
        sum += input[i].to_i
    end
end

puts sum