#!/usr/bin/env ruby

total = 1000
$square = Array.new(total){Array.new(total){Array.new}}
$ids = Array.new

def read_line(line)
  split = line.split
  id = split[0][1..-1]
  edges = split[2].split(",")
  edges[1].chomp!(":")
  sides = split[3].split("x")
  {:id => id, :edges => edges, :sides => sides}
end


def fill_squares(line_obj)
  $ids.push(line_obj[:id])
  left_start = line_obj[:edges][0].to_i
  left_end = left_start + line_obj[:sides][0].to_i - 1
  top_start = line_obj[:edges][1].to_i
  top_end = top_start + line_obj[:sides][1].to_i - 1
  for i in top_start..top_end
    for j in left_start..left_end
      $square[i][j].push(line_obj[:id])
    end
  end
end

File.open("input/3.txt", "r") do |f|
  f.each_line do |line|
    values = read_line(line)
    fill_squares(values)
  end
end

filter1 = $square.map{|a| a.select{ |x| x == nil || x.length > 1}}
filter2 = filter1.select{|a| a.any?}.flatten.uniq
puts $ids - filter2
