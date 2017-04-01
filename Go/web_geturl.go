package main

import (
	"os"
	"net/http"
	"fmt"
	"io/ioutil"
	"time"
)

func main() {
	for _, url := range os.Args[1:] {
		start := time.Now()
		resp, err := http.Get(url)
		if err != nil {
			fmt.Fprint(os.Stderr, "fetch:%v \n",err)
			os.Exit(1)
		}

		body, err := ioutil.ReadAll(resp.Body)  //获取html的body,resp.Body包含了一个服务器的响应流
		resp.Body.Close()                //关闭，防止资源泄露
		if err != nil {
			fmt.Fprint(os.Stderr, "fetch body :%v\n",err)
			os.Exit(1)
		}

		fmt.Printf("%s",body)
		fmt.Printf("共用时: %.2fs\n",time.Since(start).Seconds())
	}
}