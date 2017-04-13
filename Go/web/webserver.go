package main

//简单的服务器程序,计算访问次数

import (
	"net/http"
"fmt"
"log"
	"sync"
)
var mu sync.Mutex
var count int
func main() {
	http.HandleFunc("/", handler)
	http.HandleFunc("/count", counter)   //访问localhost:8000/count输出访问次数
	log.Fatal(http.ListenAndServe("localhost:8000", nil))
}
// handler echoes the Path component of the requested URL.
func handler(w http.ResponseWriter, r *http.Request) {
	mu.Lock()
	count++
	mu.Unlock()
	fmt.Fprintf(w, "URL.Path = %q\n", r.URL.Path)
}
// counter echoes the number of calls so far.
func counter(w http.ResponseWriter, r *http.Request) {
	mu.Lock()
	fmt.Fprintf(w, "Count %d\n", count)
	mu.Unlock()
}