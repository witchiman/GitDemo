// 计算一个十进制数转换为二进制数所含1的个数
package main

import (
	"fmt"
	"strconv"
)
var pc [256]byte
func init() {
	for i := range pc {
		pc[i] = pc[i/2] + byte(i&1)
	}
}

func PopCount(n int) int {
	return int(
		pc[byte(n>>(0*8))] +
		pc[byte(n>>(1*8))] +
		pc[byte(n>>(2*8))] +
		pc[byte(n>>(3*8))] +
		pc[byte(n>>(4*8))] +
		pc[byte(n>>(5*8))] +
		pc[byte(n>>(6*8))] +
		pc[byte(n>>(7*8))])
}

func main()  {
	fmt.Print("The 1 which the integer inclue is :" +  strconv.Itoa(PopCount(16)))
}






