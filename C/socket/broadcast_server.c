#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <sys/time.h>
#include <sys/select.h>
#include <netinet/in.h>
#include <string.h>

#define PORT 8888
#define IP_FOUND "IP_FOUND"
#define IP_FOUND_ACK "IP_FOUND_ACK"

int main(void)
{
    int sock, ret;
    struct sockaddr_in local_addr;
    struct sockaddr_in from_addr;
    int from_len;
    char buf[32];
    fd_set readfd;
    struct timeval timeout;
    timeout.tv_sec = 5;
    timeout.tv_usec = 0;

    sock = socket(AF_INET, SOCK_DGRAM, 0);
    if(sock == -1)
    {
		perror("sock()");
		exit(EXIT_FAILURE);
    }

    memset(&local_addr, 0, sizeof(struct sockaddr_in));
    local_addr.sin_family = AF_INET;
    local_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    local_addr.sin_port = htons(PORT);

    ret = bind(sock, (struct sockaddr *)&local_addr, sizeof(local_addr));
    if(ret == -1)
    {
		perror("bind()");
		exit(EXIT_FAILURE);
    }

    printf("The server has beed ready!\n");
    while(1)
    {
        FD_ZERO(&readfd);   //文件描述符集合清零
		FD_SET(sock, &readfd);   //将套接字加入读集合

	/*监听是否有数据到来*/
		ret = select(sock+1, &readfd, NULL, NULL, &timeout);
        switch(ret)
		{
			case -1:
				perror("select()");
				break;
			case 0:
				break;
			default:
			/*有数据到来*/
				if(FD_ISSET(sock, &readfd))
				{
					recvfrom(sock, buf, 32, 0,(struct sockaddr *) &from_addr, &from_len);
					if(strstr(buf, IP_FOUND)) //判断是否IP_FOUND
					{
						memcpy(buf, IP_FOUND_ACK, strlen(IP_FOUND_ACK));
						sendto(sock, buf, strlen(buf), 0, (struct sockaddr *)&from_addr, from_len); //发送给客户端
					}
				}	    
		}
    }

 printf("SERVER: IP FOUND\n");
 return 0;

}



















