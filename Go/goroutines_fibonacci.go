package main

import (
	"time"
	"fmt"
)

func main()  {
	go spinner(10 * time.Millisecond) //start a goroutine

	count := 45
	result := fib(count)

	fmt.Printf("\r fibonacci(%d) = %d\n", count, result)
}

func spinner(delay time.Duration) {
	for {
		for _, s := range `_\|/` {  //output the symbol
			fmt.Printf("\r%c", s)
		}

	}
}

func fib(n int) int {
	if n < 2 {
		return n
	}
	return fib(n-1) + fib(n-2)
}
