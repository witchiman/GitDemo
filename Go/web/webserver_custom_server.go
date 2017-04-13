package main

import (
	"net/http"
	"log"
	"io"
	"time"
)


func main()  {
	server := http.Server{
		Addr: ":8080",
		Handler: &handle{},
		ReadTimeout: 5*time.Second,
	}

	err := server.ListenAndServe()
	if err != nil{
		log.Fatal(err)
	}
}

type handle struct {}

func (*handle)ServeHTTP(w http.ResponseWriter, r *http.Request)  {
	io.WriteString(w, "URL: " + r.URL.String())
}


