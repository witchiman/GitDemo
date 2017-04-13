
/*通过一个闭包函数实现拦截host的功能*/
package main

import "net/http"

func SingHost(handler http.Handler, allowedHost string) http.Handler {
	f := func(w http.ResponseWriter, r *http.Request) {
		 host := r.Host
		if host == allowedHost {
			handler.ServeHTTP(w, r)
		}else {
			w.WriteHeader(403)
		}
	}
	return  http.HandlerFunc(f)
}

func testFunc(w http.ResponseWriter, r *http.Request)  {
	w.Write([]byte("Hello Eve!\n"))
}

func main()  {
	handler := SingHost(http.HandlerFunc(testFunc), "localhost:8080")
	http.ListenAndServe(":8080", handler)
	 
}
