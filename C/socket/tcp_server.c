#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>
#include <netinet/in.h>
#include <sys/uio.h>

#define PORT 8888
#define BACKLOG 2

/*send和write*/
void process_conn(int s);
/*对应于recv和send函数*/
void process_conn_1(int s);
 
/*对于应于readv和writev函数*/
void process_conn_2(int s);
struct iovec * vs = NULL;

/*recvmsg和sendmsg函数*/
void process_conn_3(int s);

int main(int arg, char *argv[])
{ 
    int ss, sc;
    struct sockaddr_in server_addr, client_addr;
    int err;
    pid_t pid;

    ss = socket(AF_INET, SOCK_STREAM, 0);
    if(ss == -1)
    {
	perror("Create socket error!\n");
	return -1;
    }

    memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY); //IP为本地地址
    server_addr.sin_port = htons(PORT);
    
    err = bind(ss,(struct sockaddr*)&server_addr, sizeof(server_addr));
    if(err == -1)
    {
        perror("Bind error!\n");
        return -1;	
    }    
    
    err = listen(ss, BACKLOG);
    if(err == 1)
    {
       perror("Listen error!\n");
       return -1;
    }

    while(1)
    {
        int addrlen = sizeof(struct sockaddr);
	sc = accept(ss, (struct sockaddr*)&client_addr, &addrlen);
	if(sc == -1)
	{
	    continue;
	}
       
        printf("Server has startuped:\n");  
      
        pid = fork();
	if(pid == 0)
	{
	    close(ss);
	    process_conn_3(sc);
	} else
	{   
            free(vs); //对应于readv和writev函数
            close(sc);
	}

    }


    return 0;
}

void process_conn(int s)
{
    ssize_t size;
    char buf[1024];

    while(1)
    {
        size = read(s, buf, strlen(buf)); //接收客户端的数据，放到缓冲区
        if(size ==0)
            return;
        sprintf(buf, "received %ld bytes!\n", size);
        write(s, buf, strlen(buf)+1);    //响应客户端                  
    }
}


void process_conn_1(int s)
{
    ssize_t size;
    char buf[1024];
    
    while(1)
    {
	size = recv(s, buf, 1024, 0);
	if(size == 0)
	    return;

	sprintf(buf, "Recv %ld bytes!\n", size);
	send(s, buf, strlen(buf)+1, 0);
    }
    
}

void process_conn_2(int s)
{  
    ssize_t size;
    char buf[30];
    struct iovec *v = (struct iovec*)malloc(3*sizeof(struct iovec));
    if(v == NULL)
    {
	perror("The memory is full!\n");
	return;
    }

    vs = v;       //交由vs释放内存
    /*初始化iovec*/
    v[0].iov_base = buf;   
    v[1].iov_base = buf + 10;
    v[2].iov_base = buf + 20;
    v[0].iov_len = v[1].iov_len = v[2].iov_len = 10;
    
    while(1)
    {
	size = readv(s, v, 3);
	if(size ==0)
	    return;

        printf("Received the msg from client:\n");
        write(1, v[0].iov_base, 10);  //输出客户端发来的消息到屏幕        

        /*设置响应消息*/
	sprintf(v[0].iov_base, "Readv");
	sprintf(v[1].iov_base, " %ld ",size);
	sprintf(v[2].iov_base,"bytes!\n");
        
	v[0].iov_len = strlen(v[0].iov_base);
	v[1].iov_len = strlen(v[1].iov_base);
	v[2].iov_len = strlen(v[2].iov_base);

        writev(s, v, 3);           //发送给客户端

    }
}   


void process_conn_3(int s)
{
    ssize_t size;
    char buf[30];
    struct msghdr msg;
    
    struct iovec *v = (struct iovec*)malloc(3*sizeof(struct iovec));
    if(v == NULL)
    {
	perror("The memory is full!\n");
	return;
    }
    
    vs = v;   //挂接全局变量，便于释放空间    

    /*初始化消息*/
    msg.msg_name = NULL;   //没有名字域
    msg.msg_namelen = 0;
    msg.msg_control = NULL;   //无控制域
    msg.msg_controllen = 0;
    msg.msg_iov = v;
    msg.msg_iovlen = 30;   //缓冲区长度为30
    msg.msg_flags = 0;   //无特殊操作

    v[0].iov_base = buf;
    v[1].iov_base = buf + 10;
    v[2].iov_base = buf + 20;
    v[0].iov_len = v[1].iov_len = v[2].iov_len = 10;

    while(1)
    {
        printf("Be ready to receive the msg:\n");
	size = recvmsg(s, &msg, 0);  //采用默认数据接收方式
	if(size == 0)
	    return;
        
        printf("Receive the msg from client:\n");
        write(1, v[0].iov_base, size);  //输出客户端的消息到屏幕          

	sprintf(v[0].iov_base, "Recvmsg");
	sprintf(v[1].iov_base," %ld ",size);
	sprintf(v[2].iov_base,"bytes!\n");
        
	v[0].iov_len = strlen(v[0].iov_base);
	v[1].iov_len = strlen(v[1].iov_base);
	v[2].iov_len = strlen(v[2].iov_base);

        sendmsg(s, &msg, 0); //发送给客户端

    }


} 
















