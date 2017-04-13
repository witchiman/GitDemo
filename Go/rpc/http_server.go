package main

import (
	"errors"
	"net/rpc"
	"net/http"
	"fmt"
)

type Factors struct {
	X, Y int
}

type Result struct {
	Quotient, Remainder int
}

type Math int

/*
*  定义的方法须满足以下规则
*  func (t *T) MethodName(argType T1, replyType *T2) error
×  才会被注册到RPC接口里
*/

func (m *Math)Multiply(f Factors, product *int) error {
	*product = f.X * f.Y
	return  nil
}

func (m *Math)Divide(f Factors, result *Result) error {
	if f.Y == 0 {
		return errors.New("Divide by zero!")
	}
	result.Quotient = f.X / f.Y
	result.Remainder = f.X % f.Y
	return nil
}

func main()  {
	math := new(Math)
	rpc.Register(math)   //注册对象
	rpc.HandleHTTP()

	err := http.ListenAndServe(":1234", nil)
	if err != nil {
		fmt.Println(err.Error())
	}
}
