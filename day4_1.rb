#!/usr/bin/env ruby

valid_passphrases = 0
line = ""
loop do
    line = gets.chomp
    break if line == "exit"

    words = line.split
    if words.uniq.length == words.length
        valid_passphrases += 1
    end
end

puts valid_passphrases