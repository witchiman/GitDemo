import java.util.Arrays;

/**
 * 希尔排序也成为“缩小增量排序”，其基本原理是，现将待排序的数组元素分成多个子序列，使得每个子序列的元素个数相对较少，
 * 然后对各个子序列分别进行直接插入排序，待整个待排序列“基本有序”后，最后在对所有元素进行一次直接插入排序。因此，
 * 我们要采用跳跃分割的策略：将相距某个“增量”的记录组成一个子序列，这样才能保证在子序列内分别进行直接插入排序后
 * 得到的结果是基本有序而不是局部有序。希尔排序是对直接插入排序算法的优化和升级。
 */
public class Main {

    public static void main(String[] args) {
        int[] data = new int[] { 26, 53, 67, 48, 57, 13, 48, 32, 60, 50 };
        shellSort(data);
        System.out.println(Arrays.toString(data));
    }

    static void shellSort(int[] data) {
        int temp = 0;
        int j = 0;
        for (int increment = data.length/2; increment>0; increment/=2) {
            for (int i=increment; i<data.length; i++) {
                temp = data[i];
                for (j=i-increment; j >=0 && data[j]>temp; j-=increment) {
                    data[j+increment] = data[j];
                }
                data[j+increment] = temp;
            }
        }
    }

}