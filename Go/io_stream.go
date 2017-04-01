package main

import (
	"fmt"
	"os"
	"bufio"
	"strconv"
)

func countLines(f *os.File, counts map[string]int) {
	input := bufio.NewScanner(f)
	for input.Scan() {
		counts[input.Text()]++
	}
}

func main()  {
	 files := os.Args[1:]
	 counts := make(map[string]int)
	 fmt.Print("here the program begin:\n")

	if len(files) == 0 {
		countLines(os.Stdin, counts)
	} else {
		for _,file := range files {
			f, err := os.Open(file)
			if err != nil {
				fmt.Fprint(os.Stderr, "The error is %v\n",err)
				continue
			}
			countLines(f, counts)
			fmt.Print("The map size is " + strconv.Itoa(len(counts)))
			f.Close()
		}
	}

	for key, value := range counts {
		if value > 1 {
			fmt.Printf("The map is : %d\t%s\n", value, key)
		}
	}

}
