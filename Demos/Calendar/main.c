#include <stdio.h>
#include <stdlib.h>
int main(void)
{
    int year,month;
    printf("Please enter the year and the month:\n");
    while(scanf("%d%d",&year,&month) != 0)
        calCalender(year, month);
    getchar();
    return 0;
}

void calCalender(int year, int month)
{
    check(year, month);
    int year_start = 1;
    int  offset = 0;
    int leap_count = 0;
    if (year > 1600) offset = calOffset(year, month, countLeap(year));

    //printf("offset: %d\n",offset);
    switch(month)
    {
     case 1:
     case 3:
     case 5:
     case 7:
     case 8:
     case 10:
     case 12:
        output(month, 31, offset);
        break;
     case 2:
        if(isLeap(year))
        {
           output(month, 28, offset);
        }
        else
            output(month, 29, offset);
        break;
     case 4:
     case 6:
     case 9:
     case 11:
        output(month, 30, offset);
        break;
     default :
        printf("Arguments Error!");
    }

}

void check(int year, int month)
{
    if(year < 1600 || month < 1 || month >12)
    {
        printf("The argumennts you inputed are wrong,here is a wrong output,please input again!\n");
    }
}

int calOffset(int year,int month, int leap_count)
{
    int offset = 0;
    if(month <= 2)
    {
        if(isLeap(year))
            offset = year - 1601 + leap_count;
        else
            offset = year - 1600 + leap_count;
    }
    else
    {
        offset = year - 1601 + leap_count;

    }
}

int isLeap(int year)
{
    if((year%100!=0 && year %4 ==0) || (year%400==0))
    {
        return 1;
    }else
        return 0;
}

int countLeap(int year)
{
    int leap_count = 0;
    int i;
    for(i=1600; i<=year; i++)
    {
        if(isLeap(i)) leap_count++;
    }
    //printf("leaps :%d\n",leap_count);
    return leap_count;
}

void output(int month, int day, int offset)
{

    int first_days[] = {5, 1, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
    int first_day = 0;
    int i,j;
    printf("------------%d----------\n",month);
    printf ("Mon Tue Wed Thu Fri Sat Sun \n");
    first_day = (first_days[month-1] + offset) % 7;
    // printf("%d",first_day);
    for(i=0;i<first_day;i++)
    {
        printf("    ");
    }
    for(j=1; j<=day; j++)
    {
        printf("%-4d",j);
        if((j+first_day)%7 == 0)  printf("\n");
    }
    printf("\n");
}
