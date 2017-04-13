/*在正常响应后追加一些内容*/
package main

import "net/http"

type AppendMiddleware struct {
	handler http.Handler
}

func (this *AppendMiddleware)ServeHTTP(w http.ResponseWriter, r *http.Request)  {
	this.handler.ServeHTTP(w, r)
	w.Write([]byte("It's a middleware test!\n"))
}

func testFunc(w http.ResponseWriter, r *http.Request)  {
	w.Write([]byte("Hello Eve!\n"))
}

func main()  {
	handler := &AppendMiddleware{http.HandlerFunc(testFunc)}
	http.ListenAndServe(":8080", handler)
	 
}
