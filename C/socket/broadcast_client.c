#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <sys/select.h>
#include <unistd.h>
#include <netinet/in.h>
#include <string.h>
#include <sys/ioctl.h>
#include <linux/if.h>
#include <arpa/inet.h>

#define IP_FOUND "IP_FOUND"
#define IP_FOUND_ACK "IP_FOUND_ACK"
#define PORT 8888
#define IFNAME "eth0"

int main(void)
{
    int sock, ret;
    int optval = 1;   //输入参数，为1时允许广播
    struct ifreq ifr;
    struct sockaddr_in broadcast_addr;
    struct sockaddr_in from_addr;
    int from_len;
    char buf[32];
    fd_set readfd;
    struct timeval timeout;
    timeout.tv_sec = 2;
    timeout.tv_usec = 0;

    sock = socket(AF_INET, SOCK_DGRAM, 0);
    if(sock == -1)
    {
	perror("socket()");
	exit(EXIT_FAILURE);
    }
    
    
    strncpy(ifr.ifr_name, IFNAME, strlen(IFNAME)); //指定网络接口
    /*获取广播地址*/
    if(ioctl(sock, SIOCGIFBRDADDR, &ifr) == -1)
    {
        perror("ioctl()");
	exit(EXIT_FAILURE);
    }
    /*将获取到的广播地址赋值给broadcast_addr*/
    memcpy(&broadcast_addr, &ifr.ifr_broadaddr, sizeof(struct sockaddr_in));
    broadcast_addr.sin_port = htons(PORT);

    printf("Get address %s\n", inet_ntoa(broadcast_addr.sin_addr));    

   /*设置套接字文件描述符sock为可以进行广播操作*/
    ret = setsockopt(sock, SOL_SOCKET, SO_BROADCAST, &optval, sizeof(optval));
    if(ret == -1)
    {
        perror("setsocketopt()");
        exit(EXIT_FAILURE);
    }
    
    printf("Client start to broadcst!\n");
    int i;
    for(i=0; i<10; i++)
    {
        /*广播发送服务器地点请求*/
	ret = sendto(sock, IP_FOUND, strlen(IP_FOUND), 0, (struct sockaddr *)&broadcast_addr, sizeof(broadcast_addr));
        if(ret == -1)
	    continue;

	FD_ZERO(&readfd);
	FD_SET(sock, &readfd);

	ret = select(sock+1, &readfd, NULL, NULL, &timeout);
	switch(ret)
	{ 
	    case -1:
		perror("select()");
		break;
	    case 0:
                printf("time out!\n");
                perror("select()");
		break;
	    default:
		if(FD_ISSET(sock, &readfd))
		{
		    recvfrom(sock, buf, 32, 0, (struct sockaddr *)&from_addr, &from_len);
		    printf("Received msg from server: %s\n", buf);

		    if(strstr(buf, IP_FOUND_ACK))
		    {
			printf("The IP of server is %s\n", inet_ntoa(from_addr.sin_addr));
		    }
		}

		break;

	}
    }


    return 0;
}





