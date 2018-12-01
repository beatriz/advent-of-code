class Node
  attr_accessor :name, :weight, :children

  def initialize(name, weight, children = [])
    @name = name
    @weight = weight
    @children = children
  end

  def branch_weight
    @children.map do |c|
      c.branch_weight
    end.reduce(@weight, :+)
  end

  def common_children_weight
    value_counter = Hash.new(0)
    values = @children.map do |c|
      c.branch_weight
    end

    values.each do |v|
      value_counter[v] += 1
    end

    values.select do |v|
      v if v != value_counter.key(1)
    end[0]
  end

  def outlier
    o = nil
    @children.each do |c|
      o = c if c.branch_weight != self.common_children_weight
    end
    o
  end
end

def construct_tree(b_root)
  if (ch = Branches[b_root]).nil?
    children = []
  else
    children = ch.map do |c|
      construct_tree(c)
    end
  end

  Node.new(b_root, Weights[b_root], children)
end

def find_final_outlier(root)
  outlier = root.clone
  previous = nil
  while not (outlier = outlier.outlier).nil?
    previous = outlier.clone
  end
  previous
end

Branches = {}
Weights = {}
children = []
all = []
file = ARGV[0] || "input.txt"

open(file).each_line do |line|
  a = line.gsub(/,/, "").split
  Weights.merge!({ a[0] => Integer(a[1].gsub(/[()]/, "")) })
  all << a[0]
  if not a[3].nil?
    Branches.merge!({ a[0] => a[3..a.count] })
    children += a[3..a.count]
  end
end

Root = construct_tree((all - children)[0])
puts Root.name
puts find_final_outlier(Root).weight + (Root.common_children_weight - Root.outlier.branch_weight)