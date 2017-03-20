#include "apue.h"
#include <sys/wait.h>

int main(void)
{
	char line[MAXLINE];
	FILE *fin;

	if((fin=popen("upper", "r")) == NULL)  //upper为一个二进制文件，用来把输入的大写字母转换为小写字母
	  printf("popen error!\n");

   for(;;)
   {
	   fputs("prompt>　",stdout);
	   fflush(stdout);          //标准输出是行缓冲，由于提示信息里没有换行符，需要冲洗
	   if(fgets(line, MAXLINE, fin) == NULL)
		 break;
	   if(fputs(line, stdout) == EOF)
		 printf("fputs error to pipe!\n");
   }

   if(pclose(fin) == -1)
	 printf("pclose error!\n");
   
   putchar('\n');

	exit(0);
}

/***************************************************
*另一个二进制文件的源码
***************************************************/
#include "apue.h"
#include <ctype.h>

int main(void)
{   
	char c;

	while((c=getchar()) != EOF)
	{
		if(isupper(c))
		  c = tolower(c);

		if(putchar(c) == EOF)
		  printf("output error!\n");

		if(c == '\n')
		  fflush(stdout);
	}

	exit(0);
}
