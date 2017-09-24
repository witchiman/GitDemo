import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int[] arr = new int[]{2, 6 ,3 ,7 ,4 ,1, 9, 0 };
        List<Integer> list = GetLeastNumbers(arr, 5);
        System.out.println(list);
    }

    public static ArrayList<Integer> GetLeastNumbers(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        //检查输入的特殊情况
        if (input == null || input.length <= 0 || input.length < k) {
            return list;
        }
        //构建最大堆
        for (int len = k / 2 - 1; len >= 0; len--) {
            adjustMaxHeapSort(input, len, k - 1);
        }

        //从第k个元素开始分别与最大堆的最大值做比较，如果比最大值小，则替换并调整堆。
        //最终堆里的就是最小的K个数。
        int tmp;
        for (int i = k; i < input.length; i++) {
            if (input[i] < input[0]) {
                tmp = input[0];
                input[0] = input[i];
                input[i] = tmp;
                adjustMaxHeapSort(input, 0, k - 1);
            }
        }

        for (int j = 0; j < k; j++) {
            list.add(input[j]);
        }
        return list;
    }

    public static void adjustMaxHeapSort(int[] input, int pos, int length) {
        int temp;
        int child;
        for (temp = input[pos]; 2 * pos + 1 <= length; pos = child) {
            child = 2 * pos + 1;
            if (child < length && input[child] < input[child + 1]) {
                child++;
            }
            if (input[child] > temp) {
                input[pos] = input[child];
                input[child] = temp;
            } else {
                break;
            }
        }
    }
}