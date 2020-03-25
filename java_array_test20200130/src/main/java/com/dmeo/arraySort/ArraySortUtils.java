package com.dmeo.arraySort;

import com.dmeo.array.MyQueue;

import java.util.Arrays;
import java.util.Deque;
import java.util.Queue;

/**
 * @author Joe
 * @version 1.0
 * @date 2020/2/4 19:23
 */
public class ArraySortUtils {
    /**
     * 冒泡排序
     * @param a
     */
    public static void bubbleSort(int[] a) {
        // 总共比较几轮（数组长度-1）
        for (int i = 0; i < a.length-1; i++) {
            // 每次比较的次数
            for (int j = 0; j < a.length-1-i; j++) {
                if (a[j] > a[j+1]) {
                    int temp = a[j+1];
                    a[j+1] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

    /**
     * 快速排序
     * @param a
     * @param start 第一个
     * @param end 最后一个
     */
    public static void quickSort(int[] a, int start, int end) {
        if(start < end) {
            int standard = a[start]; // 标准值
            int low = start; // first
            int high = end; // last
            // 如果起始小于最后
            while(low < high) {
                // 前小于后面的并且后面的值大于等于标准值，后面的坐标减一
                while (low < high && a[high] >= standard) {
                    high--;
                }
                // 后面的值小于标准值，把后面的值赋予前面的值
                a[low] = a[high];
                // 前小于后面的并且前面的值小于等于标准值，后面的坐标减一
                while(low < high && a[low] < standard) {
                    low++;
                }
                // 前面的值打于标准值，把前面的值赋予后面面的值
                a[high] = a[low];
            }
            // 当前面的坐标和后面的坐标重合，把重合坐标的值变为标准值
            a[low] = standard;
            // 递归左右
            quickSort(a,start,low);
            quickSort(a,low+1,end);
        }

    }

    /**
     * 插入排序
     * @param a
     */
    public static void insertSort(int[] a) {
        for (int i = 1;i < a.length; i++) {
            // i前面的值
          int j = i-1;
          // 比较值
          int compare = a[i];
          // 下表>=0并且后一个数大于比较值
          while(j >=0 && compare < a[j]) {
              // 后一个数和前一个数交换
              a[j+1] = a[j];
              // 下表减1
              j--;
          }
          // 最后前面的值变为比较值
          a[j+1] = compare;
        }
    }

    /**
     * 希尔排序
     * @param a
     */
    public static void shellSort(int[] a) {

        // 步长循环的次数
        for (int d = a.length/2; d > 0; d = d/2) {
            // 数组分组循环的次数
            for (int j = d; j < a.length; j++) {
                // 每个分组里的数进行比较
                for( int k = j-d; k >=0;k=k-d) {
                            if(a[k] > a[k+d]) {
                                int temp = a[k];
                                a[k]  =a[k+d];
                                a[k+d] = temp;
                            }
                }

            }
        }
    }

    /**
     * 希尔排序改
     * @param a
     */
    public static void shellSort1(int[] a) {

        // 步长循环的次数
        for (int d = a.length/2; d > 0; d = d/2) {
            // 数组分组循环的次数

            for (int j = 0; j < d; j++) {
                // 每个分组里的数进行比较
                for( int k = j+d; k < a.length;k=k+d) {
                   int f = k-d;
                   int compare = a[k];
                   while(f>=j && a[f] > compare) {
                       a[f+d]  = a[f];
                       f = f-d;
                   }
                   a[f+d] = compare;
                }

            }
        }
    }

    /**
     * 选择排序
     * @param a
     */
    public static void selectSort(int[] a) {
        int minIndex  = 0;

        for(int i = 0; i<a.length;i++) {
            minIndex = i;
            for(int j = i+1;j<a.length;j++) {
                if(a[minIndex] > a[j]) {
                    minIndex = j;
                }
            }
            int temp = a[i];
          a[i] = a[minIndex];
            a[minIndex] = temp;
        }

    }

    /**
     * 数组归并
     * 两个有序数组叠加成一个
     * 对这个数组排序
     * @param a
     * @param start 第一个数组起始
     * @param middle 第一个末尾
     * @param end 第二个末尾
     */
    public static void merge(int[] a,int start, int middle, int end) {
        // 新建一个数组
        int newArray[] = new int[end-start+1];
        int index = 0; // 数组下标
        int i = start; // 第一个数组下标
        int j = middle+1;// 第二个数组下标
        // 第一个数组下标或者第二个数组下标未越界
        while(i<=middle && j<=end) {

//            第一个数组第i个小于第二个数组第j个
            if(a[i] < a[j]) {
                // 新数组第index个数未a【i】，i+1
                newArray[index]  =a[i];
                i++;
            }else{
                newArray[index] = a[j];
                j++;
            }
            // index+1
            index++;
        }
        // 如果第一个数组还有余，后面的数都加到新数组，如下同理
        while(i<=middle){
            newArray[index] = a[i];
            i++;
            index++;
        }
        while(j<=end) {
            newArray[index] = a[j];
            j++;
            index++;
        }
        // 原数组排序
        for(int k = 0;k<newArray.length;k++){
            a[k+start] = newArray[k];
        }
    }

    /**
     * 归并排序
     * @param a
     */
    public static void mergeSort(int[] a, int start,int end) {
        if (start<end) {
            int middle = (end-start)/2+start;
            mergeSort(a,start,middle);
            mergeSort(a,middle+1,end);
            merge(a,start,middle,end);
        }

    }

    /**
     * 基数排序
     * @param a
     */
    public static void radixSort(int[] a) {

        int max = Integer.MIN_VALUE;
        // 存放临时数据
        int temp[][] = new int[10][a.length];
        // 临时数据数量保存
        int count[] = new int[10];
        int index = 0;
        for(int i = 0; i < a.length; i++) {
            if(a[i] > max) {
                max = a[i];
            }
        }
        int length = (max+"").length();
        // 总共循环的次数，依据数组最大数的位数
        for(int i =0, n = 1; i < length;n = n*10, i++) {
            // 找出数据中对应位数的大小
            for( int j = 0; j < a.length; j++) {
                int remainder  = a[j]/n%10;
                // 把对应数据存到二维数组
                temp[remainder][count[remainder]++] = a[j];
            }
           // 把二维数组中的数据重新放到数组中

           for(int k = 0;k < temp.length; k++) {
               // 根据count中的数据来取出对应temp[l]中数据的个数
               for(int l = 0;l < count[k];l++) {
                   a[index++] = temp[k][l];
               }
           }
           index = 0;
           count = new int[10];
        }

    }

    /**
     * 基数排序(列队方式)
     * @param a
     */
    public static void radixQueueSort(int[] a) {

        int max = Integer.MIN_VALUE;
        // 存放临时数据
        MyQueue[] queues = new MyQueue[10];
        // 临时数据数量保存
        int index = 0;
        for(int i = 0; i < a.length; i++) {
            if(a[i] > max) {
                max = a[i];
            }
        }
        int length = (max+"").length();
        // 总共循环的次数，依据数组最大数的位数
        for(int i =0, n = 1; i < length;n = n*10, i++) {
            // 找出数据中对应位数的大小
            for( int j = 0; j < a.length; j++) {
                int remainder  = a[j]/n%10;
                // 把对应数据存到对应的队列
                if(queues[remainder] == null){
                    queues[remainder] = new MyQueue();
                }

                queues[remainder].push(a[j]);
            }
            // 把列队的数据重新放到数组中

            for(int k = 0;k < queues.length; k++) {
                if(queues[k] != null) {
                    while(!queues[k].isEmpty()) {
                        a[index] = queues[k].poll();
                        index++;
                    }
                }


            }
            index = 0;
        }

    }

    /**
     * 堆排序
     * @param a
     */
    public static void HeapSort(int[] a) {
        // 起始比较点
        int start = a.length/2-1;
        // 有子节点的点都需要比较
        for(int i = start;i>=0;i--) {
            HeapChange(a,a.length,i);
        }
        /**
         * 数值第一个和最后一个交换，然后去除最后一个继续进行大顶堆转换
         */
        for (int i = 0;i<a.length-1;i++) {
            int temp = a[0];
            a[0] = a[a.length-1-i];
            a[a.length-1-i] = temp;
            HeapChange(a,a.length-i,0);
        }
    }

    /**
     * 转换成大顶堆
     * @param a
     * @param size
     * @param index
     */
    public static void HeapChange(int[] a,int size, int index) {
        // 左子节点
        int left = index*2+1;
        // 右子节点
        int right = index*2+2;
        // 最大节点下标
        int max = index;
        /**
         * 当前节点和左子节点，右子节点比较，比当前节点数值大节交换位置
         */
        if(left < size-1 && a[left] > a[max]) {
            max = left;
        }
        if(right < size-1 && a[right] > a[max]) {
            max = right;
        }
        if (max != index) {
            int temp = a[index];
            a[index] = a[max];
            a[max] = temp;
            // 如果子节点也有子节点，继续比较
            HeapChange(a,size,max);
        }
    }
    public static void main(String[] args) {
        int a[] = {36,112,4,206,33,22,11,3,23,442,1};
//        int a[] = {3,1};
        System.out.println(Arrays.toString(a));
//        merge(a,0,0,1);
        HeapSort(a);
        System.out.println(Arrays.toString(a));
    }
}
