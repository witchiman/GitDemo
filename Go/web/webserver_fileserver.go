/*实现一个静态服务器*/
package main

import (
	"net/http"
	"io"
	"os"
	"log"
)


func main()  {
	wd, err := os.Getwd()  //获取当前路径
	if err!=nil{
		log.Fatal(err)
	}

	mux := http.NewServeMux()
	mux.Handle("/", &handle{})
	mux.Handle("/static/", http.StripPrefix("/static",
		http.FileServer(http.Dir(wd))))

	err = http.ListenAndServe(":8080", mux)
	if err!=nil{
		log.Fatal(err)
	}
}

type handle struct {}

func (*handle)ServeHTTP(w http.ResponseWriter, r *http.Request)  {

	io.WriteString(w, "URL: " + r.URL.String())
}


