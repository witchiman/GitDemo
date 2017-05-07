
/* 要找出10000以内所有的素数，这里使用的方法是筛法，即从2开始每找到一个素数就标
 * 记所有能被该素数整除的所有数。直到没有可标记的数，剩下的就都是素数。下面以找出
 * 10000以内所有素数为例， 借用 CSP 方式解决这个问题
 */
package main

import (
	"fmt"
	"time"
)

func main()  {
	start := time.Now()

	origin, wait := make(chan int), make(chan struct{})

	Processor(origin, wait)

	for i:=2; i< 10000; i++ {
		origin <- i
	}
	close(origin)

	<- wait
	runtime := time.Since(start)
    fmt.Printf("运行了%v s", runtime)
}
func Processor(seq chan int, wait chan struct {}) {
	go func() {
		prime, ok := <-seq
		if !ok{
			close(wait)
			return
		}

		fmt.Println(prime)
		out := make(chan int)
		Processor(out, wait)

		for num := range seq {
			if num % prime != 0 {
				out <- num
			}
		}

		close(out)
	}()
}
