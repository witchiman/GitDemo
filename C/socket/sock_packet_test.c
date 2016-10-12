/**
* 需要以root身份运行程序
* 设置套接口以捕获链路帧的编程方法
* 从套接口读取链路帧
* 定位IP包头
* 定位TCP报头
* 定位UDP报头
* 定位应用层报文数据
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/ioctl.h>
#include <net/if.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <linux/if_ether.h>
#include <linux/ip.h>
#include <linux/tcp.h>
#include <linux/udp.h>

#define IFNAME "eth0"

int main() 
{
    int fd, ret;    
    struct ifreq ifr;
    strcpy(ifr.ifr_name, IFNAME);
    fd = socket(AF_INET, SOCK_PACKET, htons(0x0003)); //第三个参数表示截取所有的包
    
    ret = ioctl(fd, SIOCGIFFLAGS, &ifr); //获得eth0的标志位
    if(ret < 0)
    {
	close(fd);
	perror("Cant't get the flags!\n");
	return -1;
    }

    ifr.ifr_flags |= IFF_PROMISC; //在保留原来的情况下，在标志位中加入“混杂”方式

    ret = ioctl(fd, SIOCSIFFLAGS, &ifr); //将标志位写入
    if(ret < 0)
    {
	perror("Promiscuous set error!\n");
	return -2;
    }

    char buf[ETH_FRAME_LEN]; //定义一个缓冲区
    struct ethhdr *p_ethhdr; 
    int n;
    p_ethhdr = (struct ethhdr *)buf;//指向缓冲区
    n = read(fd, buf, ETH_FRAME_LEN);
    
    if(n < 0)
    {
	perror("read()");
	exit(EXIT_FAILURE);
    }

   /*输出目的MAC地址*/
   printf("dest MAC:\n");
   int i;
   for(i=0; i<ETH_ALEN-1; i++)
   { 
       printf("%02x-",p_ethhdr->h_dest[i]);
   }
   printf("%02x\n", p_ethhdr->h_dest[ETH_ALEN-1]);
   
   /*输出源MAC地址*/
   printf("source MAC:\n");
   for(i=0; i<ETH_ALEN-1; i++)
   { 
       printf("%02x-",p_ethhdr->h_source[i]);
   }
   printf("%02x\n", p_ethhdr->h_source[ETH_ALEN-1]);
    
   /*输出协议类型,0x0800为IP协议,0x0806为ARP协议,0x0835为RARP协议*/
   printf("protocol:0x%04x\n",ntohs(p_ethhdr->h_proto));


   /*打印IP报文*/
   struct iphdr *p_iphdr = NULL;
   printf("=====IP======\n");
   if(ntohs(p_ethhdr->h_proto) == 0x0800)
   {
       p_iphdr = (struct iphdr *)(buf + ETH_HLEN); //ETH_HLEN为以太网头部总长度
       
	   char *c =(char *) &p_iphdr->saddr;  
       printf("src ip: %u.%u.%u.%u\n",c[0]&0xff, c[1]&0xff, c[2]&0xff, c[3]&0xff);    //地址转换成点十进制或使用如下方法转换
        /*printf("src ip: %u.%u.%u.%u\n",                  
	      (p_iphdr->saddr&0x000000ff)>>0,
	      (p_iphdr->saddr&0x0000ff00)>>8, 
		  (p_iphdr->saddr&0x00ff0000)>>16, 
		  (p_iphdr->saddr&0xff000000)>>24);*/ 
		  
	   c = (char *)&p_iphdr->daddr;
       printf("dest ip: %u.%u.%u.%u\n", (*c)&0xff, *(c+1)&0xff, *(c+2)&0xff, *(c+3)&0xff);
   }

  /*打印TCP报文，IP头部的protocol值为6时，为TCP协议*/ 
   printf("=====TCP======\n");
   struct tcphdr *p_tcphdr = NULL;
   if(p_iphdr->protocol == 6)
   {
       p_tcphdr = (struct tcphdr *)(p_iphdr+p_iphdr->ihl*4);  //TCP头部为IP头部偏移ihl*4
       printf("src port: %d\n", ntohs(p_tcphdr->source));
       printf("dest port: %d\n",ntohs(p_tcphdr->dest));
   }

   
  /*打印UDP报文,IP头部protocol值为17时，为UDP协议*/
  struct udphdr *p_udphdr = NULL;
  printf("======UDP======\n");   
  if(p_iphdr->protocol == 17)
  {
      p_udphdr = (struct udphdr *)(p_iphdr + p_iphdr->ihl*4);
      printf("src port: %d\n", p_udphdr->source);
      printf("dest port: %d\n", p_udphdr->dest);
  }

  /*打印应用层数据报文*/
  printf("=======APP======\n");
  char *addr = NULL;
  int len = 0;
  if(p_iphdr->protocol == 6)
  {
      addr = p_tcphdr + 20; //获取TCP协议部分的应用数据地址
      len = n - 16 - p_iphdr->ihl*4 - 20; //获得TCP协议部分的应用数据长度
  }
  else if(p_iphdr->protocol == 17)
  {
      addr = p_udphdr + p_udphdr->len; //获得UDP的应用数据地址
      len = n - 16 - p_iphdr->ihl*4 - p_udphdr->len; //获得UDP的应用数据长度
  }
  printf("Application data address: 0x%x, length: %d\n", addr, len);
  
  printf("The total length is : %d\n", n);  //以太网帧总长


   return 0;
}







