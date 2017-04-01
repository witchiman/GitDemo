/*查询一个目录下的所有文件和子目录信息及该目录总大小*/
package main

import (
	"fmt"
	"io/ioutil"
	"os"
	"path/filepath"
	"flag"
)

var fileSize = make(chan int64) //接收文件的大小信息

func dirents(dir string)[]os.FileInfo  {  //列举一个目录下的文件和子目录
	entries, err := ioutil.ReadDir(dir)
	if err!=nil {
		fmt.Fprintf(os.Stdout, "du: %v\n", err)
		return nil
	}
	return entries
}


func listDir(dir string, count int)  {  //递归遍历所有文件和子目录
	for _, entry:= range dirents(dir) {
		for i:=0;i<count; i++ {
			fmt.Print("- ")
		}

		if entry.IsDir() {
			fmt.Println("Dir:",entry.Name())
			subDir := filepath.Join(dir, entry.Name())
			listDir(subDir, count+1)             //递归遍历子目录
		} else {
			fmt.Println("File:", entry.Name())
			fileSize <- entry.Size()
		}
	}
}

func main()  {
	flag.Parse()
	count:=0       //标记遍历目录的深度
	dirs := flag.Args()  //通过命令行获取参数
	go func() {
		for _, dir := range dirs {
			listDir(dir, count)
		}
		close(fileSize)
	}()

	var nbytes,nfiles int64
	for size := range fileSize {
		nfiles++
		nbytes += size
	}

	fmt.Printf("共计 %d 个文件， 大小 %.2f GB\n", nfiles, float64(nbytes)/1e9)
}






