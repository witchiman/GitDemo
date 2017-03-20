/*
* 1.判断一个数是不是2的乘方
* 2. 计算一个二进制数里1的个数
*/

#include <iostream>
using namespace std;

/*判断是否是2的乘方*/
int power_two(int n)
{
    if(n <= 0)
    {
        return 0;
    }
    else
    {
        return (n&(n-1))==0;
    }
}

/*计算数字1的个数 */
int count_bin(int n)
{
    int i;
    for( i=0;n!=0;i++)
    {
        n &= (n-1);
    }

     return i;
}

int main( )
{ 
   int number;cout<<number<<(power_two(number)==1?" 是2的乘方!":" 不是2的乘方!")<<endl;
   cout<<count_bin(15)<<endl;
   return 0;
}