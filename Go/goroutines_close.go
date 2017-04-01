package main

import (
	"os"
	"fmt"
	"time"
)

var done = make(chan struct{})
func main()  {
	go func() {
		os.Stdin.Read(make([]byte, 1))
		close(done)    //关闭channel后，其返回零值，永远不会阻塞
	}()

	/*每隔3s输出hehe*/
	for {
		if cancelled() {  //不断查询
			fmt.Println("中断了")
			break
		}
		fmt.Println("hehe")
		time.Sleep(3 * time.Second)
	}
}

func cancelled() bool  {
	select {
	case <-done:
		return true
	default:
		return false
	}
}






