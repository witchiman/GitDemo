package main

import (
	"net/http"
	"html/template"
	"fmt"
)

const tpl = `
<html>
	<head>
		<title>hello world</title>
	</head>
	<body>
		<form method="post" action="/">
			用户名: <input type="text" name="name">
			密码: <input type="password" name="pwd">
			<button type="submit">提交</button>
		</form>
	</body>
</html>
`

func main()  {

	http.HandleFunc("/", parseForm)
	http.ListenAndServe(":8080", nil)
}

func parseForm(w http.ResponseWriter, r *http.Request)  {
	if r.Method == "GET" {
		t := template.New("test")
		t.Parse(tpl)
		t.Execute(w, nil)
	} else {
		r.ParseForm()
		fmt.Println(r.Form)
		fmt.Println(r.FormValue("name"))
	}
}