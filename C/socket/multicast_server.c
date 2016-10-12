#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>

#define MCAST_PORT 8888
#define MCAST_ADDR "224.0.0.88"  //一个局部连接多播地址
#define MCAST_DATA "Hello, this is server!\n"
#define MCAST_INTERVAL 5

int main(void)
{
    int s, err;
    struct sockaddr_in mcast_addr;
    
    s = socket(AF_INET, SOCK_DGRAM, 0);
    if(s == -1)
    {
	perror("socket()");
	return -1;
    }

    memset(&mcast_addr, 0, sizeof(struct sockaddr_in));
    mcast_addr.sin_family = AF_INET;
    mcast_addr.sin_addr.s_addr = inet_addr(MCAST_ADDR);
    mcast_addr.sin_port = htons(MCAST_PORT);

    while(1)
    {
        err  = sendto(s, MCAST_DATA, strlen(MCAST_DATA), 0,
		(struct sockaddr *)&mcast_addr, sizeof(mcast_addr));
	if(err < 0)
	{
	    perror("sendto()");
	    return -2;
	}

	sleep(MCAST_INTERVAL);
    }    

    return 0;
}
