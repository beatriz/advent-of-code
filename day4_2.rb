#!/usr/bin/env ruby

valid_passphrases = 0
line = ""
loop do
    line = gets.chomp
    break if line == "exit"

    words = line.split
    valid = true
    for i in 0..words.length
        for j in (i+1)...words.length
            if words[i].chars.sort.join == words[j].chars.sort.join
                valid = false
            end
        end
    end
    valid_passphrases += valid ? 1 : 0
end

puts valid_passphrases