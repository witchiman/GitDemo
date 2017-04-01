package main

import (
	"net"
	"log"
	"fmt"
	"bufio"
)

type client chan string

var(
	connected = make(chan client)
	disconnected = make(chan client)
	messages = make(chan string)
)

func main()  {
	listener, err := net.Listen("tcp", "localhost:8000")
	if err != nil{
		log.Print(err)
	}
	fmt.Println("server started ...")
	go broadcast()

	for {
		conn, err := listener.Accept()
		if err != nil {
			log.Print(err)
			continue
		}
		go handleConn(conn)
	}
}
/*监听全局的connected 和 disconnected 的channel来处理客户的到来和离开*/
func broadcast() {
	clients := make(map[client] bool)
	select {
	case c := <-connected:
		clients[c] = true
	case msg := <-messages:
		for c := range clients {
			c <- msg
		}
	case c:= <-disconnected:
		delete(clients, c)
		close(c)
	}
}

/*处理客户端的连接*/
func handleConn(conn net.Conn) {
	cstr := make(chan string)     //传输客户端的信息
	go responseConn(conn, cstr)
	connected <-cstr
	who := conn.RemoteAddr().String() //获取客户端的地址
	cstr <- "Your are " + who
	messages <- who + "has connected!"

	input := bufio.NewScanner(conn)
	for input.Scan(){
		messages <- who + ":" +input.Text()
	}
	disconnected <- cstr
	messages <- who + " has disconnected!"
	conn.Close()

}

/*向客户端发送数据*/

func responseConn(conn net.Conn, cstr <-chan string) {
	for str := range cstr{
		fmt.Fprint(conn, str)
	}
}






