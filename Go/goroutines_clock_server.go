package main

import (
	"net"
	"log"
	"io"
	"time"
	"fmt"
)

func main() {
	listener, err := net.Listen("tcp", "localhost:8000") //create a listen on local port 8000
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println("server started ...")

	for {
		conn, err := listener.Accept()
		if err != nil {
			log.Print(err)
			continue
		}

		go handleConn(conn)  //handle the connection from client
	}
}
func handleConn(conn net.Conn) {
	fmt.Println("create a connection...")
	defer conn.Close()

	for {
		_, err := io.WriteString(conn, time.Now().Format("15:04:05\n"))
		if err != nil {
			log.Fatal(err)
		}
		time.Sleep(time.Second)
	}
}



