package main

import (
	"net"
	"log"
	"io"
	"os"
)

func main()  {
	conn, err := net.Dial("tcp", "localhost:8000")
	if err != nil {
		log.Fatal(err)
	}

	defer conn.Close()

	doResponse(os.Stdout ,conn) //put the data getting from server into stdout
}
func doResponse(dst io.Writer, src io.Reader) {
	if _, err := io.Copy(dst, src); err !=nil {
		log.Fatal(err)
	}
}

