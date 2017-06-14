/*
 * Bit数组。
 * Go里的集合一般用map[T]bool表示，T为元素类型，这个虽然比较灵活，但是我们有更好的表现方式。
 * 当进行数据分析时，集合中的元素通常是一个非负数，并且集合经常进行并集、交集操作，这种情况下bit
 * 数组的表现远比一个map要理想。还有一个例子，如我们要执行一个http下载任务，把一个文件按照 16kb
 * 分成很多块儿，需要一个全局变量标识哪些块下载完了，此时bit数组就有了用武之地。
 */
package main

import (
	"bytes"
	"fmt"
)

type IntSet struct {
	words []uint64
}

func (s *IntSet) Has(num int) bool {
	word, bit := num/64, uint(num%64)
	return word < len(s.words) && s.words[word]&(1<<bit)!=0
}

func (s *IntSet)Add(num int)  {
	word, bit := num/64, uint(num%64)

	for len(s.words) <= word {
		s.words = append(s.words, 0)
	}
	s.words[word] |= 1<<bit
}

func (s *IntSet)Union(other *IntSet) {
	for i, tword := range other.words {
		if i < len(s.words) {
			s.words[i] |= tword
		} else {
			s.words = append(s.words, tword)
		}
	}

}

//String returns the set as a string of the form "{1 2 3}".
func (s *IntSet) String() string {
	var buf bytes.Buffer
	buf.WriteByte('{')
	for i, word := range s.words {
		if word == 0 {
			continue
		}
		for j := 0; j < 64; j++ {
			if word&(1<<uint(j)) != 0 {
				//if buf.Len() > len("{") {
				//	buf.WriteByte('}')
				//}
				fmt.Fprintf(&buf, "%d ", 64*i+j)
			}
		}
	}
	buf.WriteByte('}')
	return buf.String()
}

func main() {
	var x,y IntSet
	x.Add(1)
	x.Add(144)
	x.Add(9)
	fmt.Println(x.String())

	y.Add(9)
	y.Add(42)
	fmt.Println(y.String())

	x.Union(&y)
	fmt.Println(x.String())

}



