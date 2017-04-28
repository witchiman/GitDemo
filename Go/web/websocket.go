package main

import (

)
import (
	"golang.org/x/net/websocket"
	"log"
	"fmt"
	"net/http"
)

func Response(ws *websocket.Conn)  {
	for {
		var reply string
		if err := websocket.Message.Receive(ws, &reply); err!=nil {
			log.Fatal(err)
			break
		}

		fmt.Print("The message from client is: " + reply)

		ack := "The server has received from the msg:" + reply

		if err := websocket.Message.Send(ws, ack);err!=nil {
			log.Fatal(err)
			break

		}
	}
}

func main()  {
	http.Handle("/", websocket.Handler(Response))
	if err := http.ListenAndServe(":8080", nil); err != nil{
		log.Fatal(err)
	}
}



