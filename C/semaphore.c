/**
*线程间信号量的使用，生产消费者问题
*/
#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
void *producer_f(void *arg);
void *consumer_f(void *arg);

sem_t sem;   
int running = 1;

int main(void)
{
    pthread_t producer_t;
    pthread_t consumer_t;
    
    sem_init(&sem, 0, 16);  //信号量初始化，第二个参数为0表示只在当前进程的多个线程里共享信号量
    
    pthread_create(&consumer_t, NULL, (void*)consumer_f, NULL);
    pthread_create(&producer_t, NULL, (void*)producer_f, NULL);

    sleep(1);  //单位为秒
    running = 0;
    
    pthread_join(producer_t, NULL);
    pthread_join(consumer_t, NULL);
    sem_destroy(&sem);      //销毁信号量
    
    return 0;
}

void *producer_f(void *arg)
{
   int semval = 0;
   while(running)   
   {   usleep(1);   //单位为微秒
       sem_post(&sem);               //增加信号量的值 
       sem_getvalue(&sem, &semval);  //获得信号量的值 
       printf("Produce, total number: %d\n",semval);
   }
}

void *consumer_f(void *arg)
{
   int semval = 0;
   while(running)
   {
       usleep(1); 
       sem_wait(&sem);       //等待函数，减少信号量的值
       sem_getvalue(&sem, &semval);
       printf("Consume, total number: %d\n",semval);
   }
}
