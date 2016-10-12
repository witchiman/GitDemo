#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <string.h>
#include <sys/socket.h>
#include <signal.h>
#include <netinet/in.h>
#include <unistd.h>
#include <arpa/inet.h>
#define PORT 8888

/* */
void process_conn(int s);

/*对应于recv和send函数*/
void sig_process_1(int signal);
void sig_pipe_1(int signal);
void process_conn_1(int s);

/*对应于readv和writev函数*/
void sig_process_2(int signal);
void sig_pipe_2(int signal);
void process_conn_2(int s);
struct iovec *vc = NULL;

/*recvmsg和sendmsg函数,信号函数和结构体使用上例即可*/
void process_conn_3(int s);

int main(int arg, char *argv[])
{
    int s;
    struct sockaddr_in server_addr;
    int err;
    
    signal(SIGINT, sig_process_2); //挂接SIGINTW信号
    signal(SIGPIPE, sig_pipe_2);  //挂接SIGPIPE信号    

    if(arg ==1)
    {
	perror("Please input the IP address!\n");
	return 0;
    }	

    s = socket(AF_INET, SOCK_STREAM, 0);
    
    memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    server_addr.sin_port = htons(PORT);
    
    inet_pton(AF_INET, argv[1], &server_addr.sin_addr.s_addr);
    err = connect(s, (struct sockaddr*)&server_addr, sizeof(server_addr));
    if(-1 == err)
    {
	perror("Connect error!\n");
        return -1;
    }

    process_conn_3(s);
    close(s);
    
}

/*send和write*/
void process_conn(int s)
{
    ssize_t size;
    char buf[1024];
    
    printf("客户端，请输入数据：\n");    
      
    while(1)
    {
        size = read(0, buf, 1024);   //从标准输入读取
        
        if(size > 0)
	{
	    write(s, buf, size);
	    size = read(s, buf, 1024);
	    write(1, buf, size);          //写到标准输出
	}	    

    }
}

/*对应于recv和send*/
void sig_process_1(int signal)
{
    printf("Catch a exit signal!\n");
    _exit(0);
}

void sig_pipe_1(int signal)
{
    printf("Catch a SIGPIPE signal!\n");
    _exit(0);
}

void process_conn_1(int s)
{
    ssize_t size;
    char buf[1024];

    while(1)
    {
	size = read(0, buf, 1024);
	
	if(size > 0)
	{
            send(s, buf, size, 0);
	    size = recv(s, buf, 1024, 0);
	    write(1, buf, size);
        }
    }

}

/*readv和writev函数*/
void sig_process_2(int signal)
{
    printf("Catch a exit signal!\n");
    free(vc);
    _exit(0);   
}

void sig_pipe_2(int signal)
{
    printf("Catch a SIGPIPE signal!\n");
    free(vc);
    _exit(0);
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

    vc = v;

    v[0].iov_base = buf;
    v[1].iov_base = buf + 10;
    v[2].iov_base = buf + 20;
    v[0].iov_len = v[1].iov_len = v[2].iov_len = 10;

    while(1)
    {
        size = read(0, v[0].iov_base, 10);

	if(size > 0)
	{
            v[0].iov_len = size;
            writev(s, v, 1);        //发给服务器
            
    	    v[0].iov_len = v[1].iov_len = v[2].iov_len = 10;
	    size = readv(s, v, 3);         //从服务器接收数据
	     
	    if(size > 0)
	    {
		int i;
		for(i=0; i<3; i++)
		{
                    write(1, v[i].iov_base, v[i].iov_len);  //输出到屏幕
		}
	    }else 
            {
               printf("没有收到数据!\n");
            }	
	}
    }
}

/*recvmsg和sendmsg函数*/
/*不知为何发送数据失败*/
void process_conn_3(int s)
{
   ssize_t size;
   char buf[30];
   struct msghdr msg;
   struct iovec *v = (struct iovec*)malloc(3*sizeof(struct iovec));
   
   if(v == NULL)
       return;

   vc = v;
  
   memset(&msg, 0, sizeof(msg));
   msg.msg_name = NULL;
   msg.msg_namelen = 0;
   msg.msg_control = NULL;
   msg.msg_controllen = 0;
   msg.msg_iov = v;
   msg.msg_iovlen = 30;
   msg.msg_flags = 0;
   
   
    v[0].iov_base = buf;
    v[1].iov_base = buf + 10;
    v[2].iov_base = buf + 20;
    v[0].iov_len = v[1].iov_len = v[2].iov_len = 10;
   
    while(1)
    {
	size = read(0, v[0].iov_base, 10);
	
	if(size > 0)
	{  
            printf("Be ready to send the msg!\n"); 
	    v[0].iov_len = size;
            size = sendmsg(s, &msg, 0);
	    
            if(size <  0)
                perror("Send nothing!\n");
            else
                printf("Send %ld bytes!\n",size);   
            
            v[0].iov_len = v[1].iov_len = v[2].iov_len = 10;
	    size = recvmsg(s, &msg, 0);
            
            if(size > 0)
            {    
                printf("Received the msg from server:\n");
	        int i;
	        for(i=0; i<3; i++)
	        {
	             write(1, v[i].iov_base, v[i].iov_len);
	        }
            }else
            {
                printf("Received nothing!\n");
            }

	}
    }
} 

















