
/**
 * Created by hui on 17-4-5.
 */
public class JavaTest {


    public static void main(String[] args) throws Exception{
        int[] arr = {12,43, 123, 1, 3,6, 2,  134, 8, 19, 21, 0, 12, 4};
        //int [] arr = {3, 2, 1, 7};
        print(arr);
        mergeSort(arr, 0, arr.length-1);
        print(arr);

    }

    static void print(int[] arr) {
        for (int i=0; i<arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }

    static void mergeSort(int[] arr, int start, int end) {
        if (start < end) {
            int mid = (start+end) / 2;
            mergeSort(arr, start, mid);
            mergeSort(arr, mid+1, end);
            merge(arr, start, end);
        }
    }

    private static void merge(int[] arr, int start, int end) {
        int mid = (start+end) / 2;
        int startLeft = start;
        int startRight = mid + 1;
        int current = start;

        int[] tempArr = new int[arr.length];
        for(int i=start; i<=end; i++) {
            tempArr[i] = arr[i];
        }

        while (startLeft<=mid && startRight<=end) {
            if (tempArr[startLeft] <= tempArr[startRight]) {
                arr[current] = tempArr[startLeft];
                startLeft++;
            } else {
                arr[current] = tempArr[startRight];
                startRight++;
            }
            current++;
        }

        int remain = mid - startLeft;
        for (int i=0; i<=remain; i++) {
            arr[current+i] = tempArr[startLeft+i];
        }

    }

}


