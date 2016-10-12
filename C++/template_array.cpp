#include <iostream>
using namespace std;
 
template <typename T, int size>
int find(T (&arr)[size], T var)
{
    cout<<"The size of array is "<<size<<endl;
    for(int i=0; i<size; i++)
    {
        if(arr[i] == var)
        {
            return i;
        }
    }
    return -1;
}

int main( )
{
    int arr1[]={1,2,3,4,5};
    double arr2[]={1.1,2.2,3.3,4.2};
    cout<<"The index is "<<find(arr1,3)<<endl;
    cout<<"The index is "<<find(arr2,2.2)<<endl;
    return 0;
}