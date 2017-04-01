package main

import (
	"fmt"
	"os"
	"io/ioutil"
	"strings"
)


func main()  {
	 counts := make(map[string]int)
	 fmt.Print("here the program begin:\n")

	 for _,file := range os.Args[1:]{
		data, err := ioutil.ReadFile(file)
		if err != nil {
			fmt.Fprint(os.Stderr, "The error is %v\n",err)
			continue
		}
		 for _,line := range strings.Split(string(data), "\n") {
			counts[line]++
		 }
	 }


	for key, value := range counts {
		if value > 1 {
			fmt.Printf("The map is : %d\t%s\n", value, key)
		}
	}

}
