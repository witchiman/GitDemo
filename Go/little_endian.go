/*
 * 判断机器是小端还是大端，网络字节序为大端，
 * 一般x86结构的体系结构都为小端
 */

fun isLitteEndian() bool {
    x := 0x1234
	p := unsafe.Pointer(&x)
	p2 := (*[2]byte)(p)
	fmt.Printf("%x %x\n",p2[0],p2[1])
	if p2[0] == 0x12 {
		fmt.Println("本机器：大端")
	} else {
		fmt.Println("本机器：小端")
	} 
}
