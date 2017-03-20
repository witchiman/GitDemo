#include "apue.h"
#include <pthread.h>

int exitflag;  //set nonzero by thread to exit the program
sigset_t mask;

pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t waitloc = PTHREAD_COND_INITIALIZER;

void * fn(void *arg)
{
	int err, signo;
    int count = 1; //mask the interrupted time

	for(;;)
	{
		err = sigwait(&mask, &signo);
		if(err != 0)
		  perror("sigwait failed");

		switch(signo)
		{
			case SIGINT:
				printf("\ninterrupt %d\n",count++);
				break;
			case SIGQUIT:
				pthread_mutex_lock(&lock);
				exitflag = 1;
				pthread_mutex_unlock(&lock);
				pthread_cond_signal(&waitloc);
				return 0;
			default:
				printf("unexpected signal %d\n", signo);
				exit(1);
		}
	}
}

int main(void)
{
	int err;
	sigset_t oldmask;
	pthread_t tid;
    
	sigemptyset(&mask);
	sigaddset(&mask, SIGINT);
	sigaddset(&mask, SIGQUIT);
	if((err=pthread_sigmask(SIG_BLOCK, &mask, &oldmask)) != 0)
	  perror("SIG_BLOCK error");

	err = pthread_create(&tid, NULL, fn, 0);
	if(err != 0)
	  perror("can't create thread");

	pthread_mutex_lock(&lock);
	while(exitflag == 0)
	{
		pthread_cond_wait(&waitloc, &lock);
	}
	pthread_mutex_unlock(&lock);

    /*SIGQUIT has been caught and is now blocked,do whatever*/
    exitflag = 0;
     
	/*reset signal mask which unblocks SIGQUIT*/
    if((err=sigprocmask(SIG_SETMASK, &oldmask, NULL)) < 0)
	  perror("SIG_SETMASK error");

	return 0;
}
