# Partition-based selection algorithm to find the
# median of an array in O(N) time
# Note that this is very similar to quicksort but since
# we know which partition our element lies in, we are reducing
# the problem space by 1/2 at every step on average and thus,
# N + 1/2 x N + 1/4 x N + 1/8 x N + ...
# This sequence leads to N x (sum of series from 1...1/inf) = 
# N x (1 + 1) = 2N = O(N) running time
class Array
def median
return nil if self.empty?
    length = self.size
if length.odd?
return quickselect(0, length-1, length/2)
else
      lower = quickselect(0, length-1, length/2-1)
      higher = quickselect(0, length-1, length/2)
return (lower + higher) / 2.0
end
end
private
def partition(left, right, pivot)
    pivot_value = self[pivot]
    swap(pivot, right)
    store_index = left
    (left...right).each do |i|
if self[i] < pivot_value
        swap(i, store_index)
        store_index += 1
end
end
    swap(store_index, right)
return store_index
end
def swap(from, to)
    tmp = self[to]
self[to] = self[from]
self[from] = tmp
end
# returns kth smallest element
def quickselect(left, right, k)
return self[left] if left == right
    pivot = (right+1 + left) / 2
    new_pivot_index = partition(left, right, pivot)
    diff = (new_pivot_index - left)
if diff == k
return self[new_pivot_index]
elsif k < diff # kth element is in lower partition
return quickselect(left, new_pivot_index-1, k)
else # kth element is in higher partition
return quickselect(new_pivot_index+1, right, k-diff-1)
end
end
end
array = 10.times.map { rand(100) }
puts "sorted array: #{array.sort.inspect}, median: #{array.median}"