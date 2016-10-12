#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <net/if.h>
#include <netinet/in.h>
#include <linux/ip.h>
#include <linux/if_ether.h>
#include <arpa/inet.h>
#include <string.h>
#include <unistd.h>

#define IP_SRC "192.168.31.158"
#define IP_DEST "192.168.151.1"

struct arppacket
{
    unsigned short ar_hrd; //硬件类型
    unsigned short ar_pro; //协议类型
    unsigned char ar_hln;//硬件地址长度
    unsigned char ar_pln;//协议地址长度
    unsigned short ar_op;//ARP操作码
    unsigned char ar_sha[ETH_ALEN];//发送方MAC地址
    unsigned char ar_sip[4];  //发送方IP地址
    unsigned char ar_tha[ETH_ALEN];//目的MAC地址
    unsigned char ar_tip[4]; //目的IP地址

};

int main(void)
{
    int fd, err;
    char ef[ETH_FRAME_LEN]; //以太网缓冲区
    char eth_dest[ETH_ALEN] = {0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF};//目的以太网地址，未知，设置全为1
    char eth_source[ETH_ALEN] = {0x00, 0x0c, 0x29, 0x64, 0xd7, 0x04};//源以太网地址
    struct in_addr ip_src, ip_dest;   
 
    fd = socket(AF_INET, SOCK_PACKET, 0X0003);
    if(fd < 0)
    {
	perror("socket()");
	exit(EXIT_FAILURE);
    }
    
    struct ethhdr *p_ethhdr = (struct ethhdr*)ef; //指向以太网帧的帧头
    memcpy(p_ethhdr->h_dest, eth_dest, ETH_ALEN); //复制目的以太网地址
    memcpy(p_ethhdr->h_source, eth_source, ETH_ALEN); //复制源以太网地址
    p_ethhdr->h_proto = htons(0x0806);   //设置ARP协议类型

    struct arppacket *p_arp = (struct arppacket*)(ef + ETH_HLEN);   // 定义ARP数据报地址
    p_arp->ar_hrd = htons(0x1); //ARP硬件类型
    p_arp->ar_pro = htons(0x0800); //协议类型
    p_arp->ar_hln = 6;  //以太网地址长度
    p_arp->ar_pln = 4; //IP地址长度

    memcpy(p_arp->ar_sha, eth_source, ETH_ALEN); //源以太网地址
    inet_aton(IP_SRC, &ip_src);
    memcpy(p_arp->ar_sip, &ip_src, sizeof(ip_src));//源IP地址
    memcpy(p_arp->ar_tha, eth_dest, ETH_ALEN); //目的以太网地址
    inet_aton(IP_DEST, &ip_dest);
    memcpy(p_arp->ar_tip, &ip_dest, sizeof(ip_dest)); //目的IP地址

    printf("The IP is %s\n", p_arp->ar_tip);
    /*发送ARP请求*/
    int i;
    for(i=0; i<9; i++)
    {
	err = write(fd, ef, ETH_FRAME_LEN);
	if(err < 0)
	{
	    perror("write()");
	    break;
	}
	sleep(1);
    }

    close(fd);
    return 0;
}

