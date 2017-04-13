package main

import (
	"os"
	"fmt"
	"log"
	"net/rpc/jsonrpc"
)

type Factor struct {
	X, Y int
}

type Result struct {
	Quotient, Remainder int
}

func main()  {
	if len(os.Args) != 2 {
		fmt.Println("Usage:", os.Args[0], "server")
		os.Exit(1)
	}
	serverAddr := os.Args[1]
	client, err := jsonrpc.Dial("tcp", serverAddr+":1234")  //通过json方式连接JSON-RPC server
	if err != nil {
		log.Fatal("Dialing: ", err)
	}

	factor := Factor{12, 31}
	var product int
	err = client.Call("Math.Multiply", factor, &product)
	if err!=nil {
		log.Fatal("Math error: ",err)
	}
	fmt.Printf("Multiply: %d * %d = %d\n", factor.X, factor.Y, product)

	var result Result
	err = client.Call("Math.Divide", factor, &result)
	if err != nil {
		fmt.Println("Math error: ", err)
	}

	fmt.Printf("Divide: %d %% %d = %d remainder %d\n", factor.X, factor.Y, result.Quotient, result.Remainder)
}