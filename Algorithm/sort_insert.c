void insertSort(int arr[], int n)  
{  
    int i, j;  
    int temp;  
    for(i = 1; i < n; ++i)  
    {  
        temp = arr[i];  
        j = i - 1;  
        while(j >= 0 && temp < arr[j])  
        {  
            array[j+1] = arr[j];  
            --j;  
        }  
       if(j != i-1) arr[j+1] = temp;  
    }  
}  


void insertSort2(int arr[], int n)  
{  
    int i, j;  
	int temp;
    for (i = 1; i < n; i++)  
        if (arr[i] < arr[i - 1])  
        {  
            temp = arr[i];  
            for (j = i - 1; j >= 0 && arr[j] > temp; j--)  
                arr[j + 1] = a[j];  
            arr[j + 1] = temp;  
        }  
}