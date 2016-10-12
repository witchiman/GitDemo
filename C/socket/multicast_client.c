#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <netinet/in.h>
#include <string.h>
#include <arpa/inet.h>

#define MCAST_PORT 8888
#define MCAST_ADDR "224.0.0.88"
#define MCAST_INTERVAL 5

int main(void)
{
    int s, err;
    struct sockaddr_in local_addr, from_addr;

    s = socket(AF_INET, SOCK_DGRAM, 0);
    if(s == -1)
    {
	perror("socket()");
	return -1;
    }

    memset(&local_addr, 0, sizeof(struct sockaddr_in));
    local_addr.sin_family = AF_INET;
    local_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    local_addr.sin_port = htons(MCAST_PORT);

    err = bind(s, (struct sockaddr *)&local_addr, sizeof(local_addr));
    if(err == -1)
    {
	perror("bind()");
	return -2;
    }

    /*设置回环许可*/
    int loop = 1;
    err = setsockopt(s, IPPROTO_IP, IP_MULTICAST_LOOP, &loop, sizeof(loop));
    if(err == -1)
    {
	perror("setsockopt(): IP_MULTICAST_LOOP");
	return -3;
    }

    /*加入广播组*/
    struct ip_mreq mreq;
    mreq.imr_multiaddr.s_addr = inet_addr(MCAST_ADDR);
    mreq.imr_interface.s_addr = htonl(INADDR_ANY);  //网络接口为默认
    err = setsockopt(s, IPPROTO_IP, IP_ADD_MEMBERSHIP, &mreq, sizeof(mreq));
    if(err < 0)
    {
	perror("setsockopt():IP_ADD_MEMBERSHIP");
	return -4;
    }

    
    int addr_len =  sizeof(from_addr);
    char buf[256];
    int i;
    ssize_t size;


    /*循环接收多播组的消息*/
    for(i=1; i<=5; i++)
    {
	memset(buf, 0, 256);

	size = recvfrom(s, buf, 256, 0, (struct sockaddr *)&from_addr, &addr_len);
	if(size < 0)
	{
	    perror("recvfrom()");
	    return -5;
	}
       
        printf("Recv %dst msg from server: %s", i, buf);
	sleep(MCAST_INTERVAL);
    }

    /*退出广播组*/
    err = setsockopt(s, IPPROTO_IP, IP_DROP_MEMBERSHIP, &mreq, sizeof(mreq));
    if(err < 0)
    {
	perror("setsockopt():IP_DROP_MEMBERSHIP");
	return -6;
    }
    
    close(s);
    return 0;


}













