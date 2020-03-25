package recurrence;

import java.util.Arrays;

public class test {

//       int[] array=new int[100000000];
//       for(int i=1;i<100000000;i++){
//           array[i]=i;
//       }
//       int n=88;
//        System.out.println(1);
//     long start=System.currentTimeMillis();   //获取开始时间
//
//        System.out.println( searchT(n,array));
//
//
//       long end=System.currentTimeMillis(); //获取结束时间
//    System.out.println("程序运行时间： "+(end-start)+"ms");
//        long start1=System.currentTimeMillis();   //获取开始时间
//
//        System.out.println( searchT2(n,array,0,array.length));
//
//
//       long end1=System.currentTimeMillis(); //获取结束时间
//        System.out.println("程序运行时间： "+(end1-start1)+"ms");
//        System.out.println(2);

    //求n的阶乘void 1
    public static int forN(int n){
        int temp=1;
        if(n>=0){
            for(int i=1;i<=n;i++) {
                temp *= i;
            }
        }else{
            return -1;
        }
        System.out.println(n+"的阶乘为"+temp);
        return temp;


    }

    //阶乘递归
    public static int forN2(int n){

        if(n<0){
            return -1;
        }else{
            if(n==0){
                return 1;
            }else{
                return n*forN2(n-1);
            }
        }
    }
    public static int searchT(int n,int[] array){
        int start=0;
        int last=array.length-1;
        while(start<=last){
            int mid=(last-start)/2+start;
            if(n==array[mid]){
                return mid;
            }
            if(n>array[mid]){
                start=mid+1;
            }if(n<array[mid]){
                last=mid-1;
            }
        }
        return -1;
    }
    //二分查找递归
    public static int searchT2(int n,int[] array,int start,int last){
        int mid=(last-start)/2+start;
        if(n==array[mid]){
            return mid;
        }else if(start>last){
                return -1;
        }
        else {
            if (n > array[mid]) {
                return searchT2(n, array, mid + 1, last);
            }
            if (n < array[mid]) {
                return searchT2(n, array, start, mid - 1);
            }
        }
        return -1;
    }
    //汉诺塔问题递归
    public static void fTower(int size,String a,String b,String c){
        if(size==1){
            System.out.println("t"+size+"  "+a+"->"+c);
        }else{
            fTower(size-1,a,c,b);
            System.out.println("t"+(size)+" "+a+"->"+c);
            fTower(size-1,b,a,c);
        }

    }
    //归并查询
    //两个有序数组合成一个有序数组
    public static int[] sort(int[] a,int[] b){
        int[] c=new int[a.length+b.length];
        int numa=0;
        int numb=0;
        int numc=0;
        while(numa<a.length&&numb<b.length){
            if(a[numa]>=b[numb]){
                c[numc++]=b[numb++];
            }
            else{
                c[numc++]=a[numa++];
            }
        }
        while(numa==a.length&&numb<b.length){
            c[numc++]=b[numb++];
        }
        while(numb==b.length&&numa<a.length){
            c[numc++]=a[numa++];
        }
        return c;

    }
    //归并两个有序数组递归
    public static int[] mergeSort(int[] c,int start,int last){
        if(last>start){
            int mid=(last-start)/2+start;
            mergeSort(c,start,mid);
            mergeSort(c,mid+1,last);
            merge(c,start,mid,last);
        }
        return c;

    }
    public static void merge(int[] c,int start,int mid,int last){
        int[] temp=new int[last-start+1];
        int i=start;
        int j=mid+1;
        int k=0;
        while(i<=mid&&j<=last){
            if(c[i]<c[j]){
                temp[k++]=c[i++];
            }else{
                temp[k++]=c[j++];
            }
        }
        while(j<=last){
            temp[k++]=c[j++];
        }
        while(i<=mid){
            temp[k++]=c[i++];
        }
        System.out.println(Arrays.toString(temp));
        for(int l=0;l<temp.length;l++){
            c[l+start]=temp[l];
        }


    }
    public static void main(String... args) {
        //   fTower(3,"a","b","c");

        int[] c = {1,2,5,1};
        System.out.println(Arrays.toString(mergeSort(c,0,c.length-1)));

    }
}
