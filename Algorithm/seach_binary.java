/**
 * Created by hui on 17-4-5.
 */
public class JavaTest {


    public static void main(String[] args) throws Exception{
        int arr[] = {10, 2, 3, 4, 6, 9, 9,10, 13 };
        print(arr);
        System.out.println(binarySearchFirstRecur(arr, 9, 0, arr.length-1));


    }

    static void print(int[] arr) {
        for (int i=0;i<arr.length;i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static int binarySearch(int[] arr, int value) {
        int low = 0;
        int high = arr.length - 1;
        int mid;
        while (low <= high) {
             mid = low + (high-low) / 2; //不要写成(low+high)/2避免溢出
            if (arr[mid] > value) {
                high = mid - 1;
            } else if (arr[mid] < value) {
                low = mid + 1;
            } else {
                return mid;
            }
        }

        return low;
    }

    public static int binarySearchRecur(int[] arr, int value, int low, int high) {
        if (low > high) return low;

        int mid = (low+high) / 2;

        if (arr[mid] > value) {
            return binarySearchRecur(arr, value, low, mid-1);
        } else if (arr[mid] < value) {
            return binarySearchRecur(arr, value, mid+1, high);
        } else {
            return low;
        }

    }


    /*如果数组内有重复元素，稍微修改下上面两个方法则可*/
    public static int binarySearchFirst(int[] arr, int value) {
        int low = 0;
        int high = arr.length - 1;
        int mid;
        while (low <= high) {
            mid = low + (high-low) / 2; //不要写成(low+high)/2避免溢出
            if (arr[mid] < value) {
                low = mid + 1;
            } else if (arr[mid] >= value) {
                high = mid - 1;
            }
        }

        return low;
    }

    public static int binarySearchFirstRecur(int[] arr, int value, int low, int high) {
        if (low > high) return low;

        int mid = (low+high) / 2;

        if (arr[mid] < value) {
            return binarySearchRecur(arr, value, mid+1, high);
        } else {
            return binarySearchRecur(arr, value, low, mid-1);
        }

    }



}


