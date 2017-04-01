package main

import (
	"fmt"
)

func main()  {
	natural := make(chan int)
	sequare := make(chan int)

	go func() {
		for i:=0;i < 100; i++ {
			natural <- i
		}
		close(natural)
	}()

	go func() {
		for  x := range natural {
			sequare <- x*x
		}
		close(sequare)
	}()

	 for x:= range sequare {
		 fmt.Println(x)
	 }
}












