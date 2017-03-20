/*
 *　禁用中断字符，并将文件结束符设置为Ctrl+B
 * */
#include "apue.h"
#include <termios.h>

int main()
{   
	struct termios term;
	long vdisable;

	if(isatty(STDIN_FILENO) == 0)
		perror("standard input is not a terminal device.");

	if((vdisable=fpathconf(STDIN_FILENO, _PC_VDISABLE)) < 0)   //get the value of _PC_VDISABLE
		perror("fpathconf error or _POSIX_VDISABLE not in effect!");

	if(tcgetattr(STDIN_FILENO, &term) < 0) //fetch tty state
		perror("tcgetattr error!");
	
	term.c_cc[VINTR] = vdisable; //diable INTR charater
	term.c_cc[VEOF] = 2;         //EOF is Control-8
    
	if(tcsetattr(STDIN_FILENO, TCSAFLUSH, &term) < 0)
		perror("tcsetattr error!");
	
	exit(0);
}
