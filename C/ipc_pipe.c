#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <string.h>
int main(int argc, char* argv[] )
{
    int result,bytes;
    pid_t pid;
    int fd[2];
    char *str="hello , this is a msg from the sub process!";
    char buf[80];
    int* write_fd=&fd[1];
    int* read_fd=&fd[0];
    
    result=pipe(fd);//open the pipe 

    if(-1 == result) 
    {
	 printf("fail to open the pipe!\n");
	 return -1;
    }

    pid=fork();//create sub process
    
    if(-1 == pid)
    {
	 printf("fail to create the sub process!\n");
	 return -1;
    }

    if(0 == pid)     //run in sub process
    {
	 close(*read_fd);
	 result = write(*write_fd, str, strlen(str));
	 return 0;
    }
    else
    {
	 close(*write_fd);
	 bytes=read(*read_fd, buf, sizeof(buf));
	 printf("receive %d bytes data, the content is \"%s\"\n",bytes,buf);
    }
     	 
    return 0;
}
