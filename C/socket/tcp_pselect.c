#include <stdio.h>
#include <sys/types.h>
#include <sys/time.h>
#include <unistd.h>
#include <signal.h>

int child_events = 0;

void child_sig_handler(int x)
{
    child_events++;
    signal(SIGCHLD, child_sig_handler);
}

int main(int argc,char** argv)
{    
     int ret;
     fd_set rd;
     struct timespec ts;     
     
     ts.tv_sec = 5;
     ts.tv_nsec = 0;
 
    /*设定信号掩码sigmask和原始的信号掩码orig_sigmask*/
    sigset_t sigmask, orig_sigmask;
    sigemptyset(&sigmask);              //清空信号
    sigaddset(&sigmask, SIGCHLD);     //将SIGCHLD加入sigmask

    /*设定信号SIG_BLOCK掩码sigmask,并将原始码保存到orig_sigmask中*/
    sigprocmask(SIG_BLOCK, &sigmask, &orig_sigmask);
   /*挂接对信号SIGCHLD的处理函数child_sig_handler()*/
   signal(SIGCHLD, child_sig_handler);
   while(1)
   {
       for(; child_events>0 ;child_events-- )
           printf("程序已退出！\n");
       ret = pselect(1, &rd, NULL, NULL, &ts,&orig_sigmask);
   }
}
