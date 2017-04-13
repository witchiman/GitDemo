
//通过新建一个类型实现，只允许响应特定的host
package main

import "net/http"

type SingleHost struct {
	handler http.Handler
	allowedHost string
}

func (this *SingleHost) ServeHTTP(w http.ResponseWriter, r *http.Request)  {
	if r.Host == this.allowedHost {
		this.handler.ServeHTTP(w, r)
	} else {
		w.WriteHeader(403)
	}
}

func testFunc(w http.ResponseWriter, r *http.Request)  {
	w.Write([]byte("Hello budy!\n"))
}


func main()  {
	handler := &SingleHost{
        handler: http.HandlerFunc(testFunc), 
        allowedHost: "localhost:8080"}
	http.ListenAndServe(":8080", handler)
}
