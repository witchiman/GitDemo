package main

import "sync"

var cache = struct {
	sync.Mutex
	mapping map[string]string
}{
	mapping: make(map[string]string),
}

/*
* 关于匿名字段和内嵌方法，当执行方法时，编译器会先在当前类中这一级寻找同名方法，
*如果没有则去匿名字段的类中寻找方法。如果同一级中有两个方法，则会引起冲突，编译器报错
**/
func Lookup(key string) string {  
	cache.Lock()
	v := cache.mapping[key]
	cache.Unlock()
	return v
}

func main()  {
	
}