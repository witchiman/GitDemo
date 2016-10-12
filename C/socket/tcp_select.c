#include <stdio.h>
#include <sys/types.h>
#include <sys/time.h>
#include <unistd.h>

int main(void)
{
    fd_set rd;          //读文件集
    struct timeval tv;  //时间间隔
    int err;

    FD_ZERO(&rd);       //清空文件集
    FD_SET(0 ,&rd);     //初始化文件集

    tv.tv_sec = 5;
    tv.tv_usec = 0;

    err = select(1, &rd, NULL, NULL, &tv);

    if(err == -1)
    {
	perror("select()");
    }else if(err)
    {
	printf("Data is available now.\n");
    }else 
    {
	printf("No data within five seconds!\n"); //超时	
    }

    return 0;
    
}
