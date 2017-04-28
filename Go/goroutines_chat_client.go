package main

import (
	"net"
	"log"
	"os"
	"io"
)

func main()  {
	conn, err := net.Dial("tcp", "localhost:8000")
	if err != nil {
		log.Print(err)
	}
	done := make(chan struct{})

	go func() {
		io.Copy(os.Stdout, conn) // 忽略错误
		log.Print("done")
		done <- struct {}{}
	}()

	go myioCopy(conn, os.Stdin)
	defer conn.Close()
	<-done
}
func myioCopy(dst io.Writer, src io.Reader) {
	if _, err := io.Copy(dst, src); err != nil {
		log.Fatal(err)
	}
}
