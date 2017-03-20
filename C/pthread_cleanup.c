/*
 * 编译时连接pthread库
 * 输出时，只有第二个线程的自动清理程序被调用了，是由于第一个线程是从启动
 * 例程中返回而终止的，它的自动清理程序不会被调用
 * */
#include "apue.h"
#include <pthread.h>

void cleanup(void *arg)
{
	printf("clean up: %s\n",(char *)arg);
}

void* f1(void *arg)
{
	printf("thread 1 start:\n");
	pthread_cleanup_push(cleanup, "thread 1 first handler!\n");
	pthread_cleanup_push(cleanup, "thread 1 second handler!\n");
	printf("thread 1 complete!\n");
	
	if(arg)
	  return ((void*)1);

    pthread_cleanup_pop(0); //要和pthread_cleanup_push函数匹配，否则编译出现错误
    pthread_cleanup_pop(0);

	return ((void *)1);
}

void* f2(void *arg)
{
	printf("thread 2 start:\n");
	pthread_cleanup_push(cleanup, "thread 2 first handler!\n");
	pthread_cleanup_push(cleanup, "thread 2 seconde handler!\n");
	printf("thread 2 complete!\n");

	if(arg)
		pthread_exit((void *)2);

	pthread_cleanup_pop(0);
	pthread_cleanup_pop(0);

	pthread_exit((void *)2);
}

int main(void)
{
	int err;
	pthread_t th1, th2;
    void *ret;

	err = pthread_create(&th1, NULL, f1, (void *)1);
	if(err != 0)
	  perror("can't create thread1!\n");
   
	err = pthread_create(&th2, NULL, f2, (void *)2);
	if(err !=0)
	  perror("can't create thread2!\n");
  
	err = pthread_join(th1, &ret);
	if(err != 0)
	  perror("can't join thread1!\n");
    printf("thread 1 exit code is %ld!\n",(long)ret);

    err = pthread_join(th2, &ret);
	if(err !=0)
		perror("cant't join thread2!\n");
    printf("thread 2 exit code is %ld!\n",(long)ret);
     
	return 0;
}
