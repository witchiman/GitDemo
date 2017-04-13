package main

import (
	"net/http"
	"io"
	"log"
	"fmt"
)

func main()  {
	http.HandleFunc("/", test1)
	http.HandleFunc("/test2", test2)

	err := http.ListenAndServe(":8080",nil )
	if err!=nil{
		log.Fatal(err)
	}

}

//传统设置
func test1(w http.ResponseWriter, r *http.Request)  {
	cookie := &http.Cookie{
		Name: "cookie1",
		Value: "hello  cookie",
		Path: "/",
		Domain: "localhost",
		MaxAge: 120,
	}

	http.SetCookie(w, cookie)

	cookie, err := r.Cookie("cookie1")
	if err!=nil {
		io.WriteString(w, err.Error())
		return
	}
	io.WriteString(w, cookie.Value)
}

//header设置
func test2(w http.ResponseWriter, r *http.Request)  {
	cookie := &http.Cookie{
		Name: "cookie2",
		Value: "hello  cookie",
		Path: "/",
		Domain: "localhost",
		MaxAge: 120,
	}

	fmt.Println("str", cookie.String())

	w.Header().Set("Set-Cookie", cookie.String())
	cookie, err := r.Cookie("cookie2")
	if err!=nil {
		io.WriteString(w, err.Error())
		return
	}
	io.WriteString(w, cookie.Value)

}
