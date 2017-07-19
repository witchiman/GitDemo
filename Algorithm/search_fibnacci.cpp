#include <iostream>
#include <cstring>
using namespace std;

const int max_value = 20;

/*初始化为百斐波那契数列*/
void Fib(int *arr)
{
    arr[0] = 1;
    arr[1] = 1;
    for (int i = 2; i < max_value; i++) {
        arr[i] = arr[i-1] + arr[i-2];
    }

}

/*斐波那契查找*/
int FindFib(int *a, int n, int key)
{
    int low = 0;
    int high = n-1;
    int arr[max_value];
    Fib(arr);
    int k = 0;
    while(n > arr[k]-1)
    {
        k++;
    }
    int *temp;
    temp = new int[arr[k]-1];
    memcpy(temp, a, n*sizeof(int));
    for (int i = n; i < arr[k]-1 ; ++i)
    {
        temp[i] = a[n-1];
    }
    while(low <= high)
    {
        int mid = low + arr[k-1] -1;
        if(temp[mid] < key) {
            low = mid + 1;
            k-=2;
        } else if(temp[mid] > key) {
            high = mid - 1;
            k -= 1;
        } else {
            if(mid < n) {
                return mid;
            } else {  //说明是扩展数值，直接返回原数组最大下标
                return n-1;
            }
        }
    }

    delete temp;
    return -1;

}

int main()
{
    int a[] = {0,16,24,35,47,59,62,73,88,99};
    int key = 100;
    int index = FindFib(a, sizeof(a)/sizeof(int), key);
    cout<<"The index is "<<index;
    return 0;
}