/*自定义请求*/
package main

import (
	"net/http"
	"net/http/httptest"
)

type CustomResMiddleware struct {
	handler http.Handler
}

func (this *CustomResMiddleware)ServeHTTP(w http.ResponseWriter, r *http.Request)  {
	recorder := httptest.NewRecorder()
	//通过一个ResonseRecorder替换原来的RW
	this.handler.ServeHTTP(recorder, r)

	//先复制原来的headers
	for k,v := range recorder.Header() {
		w.Header()[k] = v
	}

	w.Header().Set("Custome-Modifier", "yayaya")  //增加一个header
	w.WriteHeader(413)				//写入状态码
	w.Write([]byte("It's a middleware test!\n"))
	w.Write(recorder.Body.Bytes())			//最后把原来的body写进去
}

func testFunc(w http.ResponseWriter, r *http.Request)  {
	w.Write([]byte("Hello Eve!\n"))
}

func main()  {
	handler := &CustomResMiddleware{http.HandlerFunc(testFunc)}
	http.ListenAndServe(":8080", handler)
	 
}
