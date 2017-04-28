/*自定义一个外部的路由器*/

package main

import (
	"net/http"
	"log"
	"io"
)


func main()  {
	mux := http.NewServeMux()
	mux.Handle("/", &handle{})
	
	mux.HandleFunc("/hello", hello)  //注册一个函数
	
	err := http.ListenAndServe(":8080", mux)	
	if err != nil {
		log.Fatal(err)

	}
}

type handle struct {}

func (*handle)ServeHTTP(w http.ResponseWriter, r *http.Request)  {
	io.WriteString(w, "URL: " + r.URL.String())
}

func hello(w http.ResponseWriter, r *http.Request)  {
	io.WriteString(w, "Hello budy!")
}

