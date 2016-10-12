#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <string.h>
#include <sys/un.h>

void display_err(const char *s)
{
    perror(s);
    exit(1);
}

int main(int arg, char *argv[])
{
    int s_unix, err;
    const char path[] = "/home/path";
    struct sockaddr_un addr;
    
    s_unix = socket(AF_UNIX, SOCK_STREAM, 0);
    if(s_unix == -1)
    {
	display_err("sockek()");
    }

    /*解除之前的绑定*/
    unlink(path);

    memset(&addr, 0, sizeof(addr));
    addr.sun_family = AF_LOCAL;
    strcpy(addr.sun_path, path);

    err = bind(s_unix, (struct sockaddr *)&addr, sizeof(addr));
    if(err == -1)
    {
	display_err("bind()");
    }

    close(s_unix);
    unlink(path);  //AF_UNIX会创建一个文件系统对象，不再需要时必须删除


    return 0;
}

