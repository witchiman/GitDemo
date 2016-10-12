#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
static int run = 1;
static int retvalue;

void *start_routine(void* arg)
{
    int *running = arg;
    printf("子线程初始化完毕，传入的参数为: %d\n",*running);
    
    while(*running)
    {
	printf("子线程正在运行。。。\n");
	usleep(1);
    }

    printf("子线程退出！\n");
    retvalue = 8;
    pthread_exit((void*)&retvalue);
}

int main(void)
{
    pthread_t pt;
    int i，ret;
    int *ret_join = NULL;
     	
    ret=pthread_create(&pt, NULL, (void*)start_routine, &run);  //建立线程
    if(-1 == ret)
    {
        printf("创建线线程失败！\n");
    }
	
    usleep(1);
    for(i=0; i<3; i++)
    {
        printf("主线程打印\n");
	usleep(1);
    }

    run = 0;
    pthread_join(pt, (void*)&ret_join);      //等待线程退出 
    printf("线程返回值是 %d\n", *ret_join);
    
    return 0;    
}
