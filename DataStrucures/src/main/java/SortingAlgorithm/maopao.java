package SortingAlgorithm;

import java.text.SimpleDateFormat;
import java.util.Date;

public class maopao {
    public static void main(String[] args) {
        int[] arr = new int[80000];
        for (int i = 0; i < arr.length; i++) {
            arr[i]=(int)(Math.random()*80000);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("排序前："+simpleDateFormat.format(date));
        paiXu2(arr);
        Date date1 = new Date();
        System.out.println("排序后："+simpleDateFormat.format(date1));
    }

    public static void paiXu(int[] arr){
        for (int i = 0; i < arr.length-1; i++) {
            for(int j=i+1;j<arr.length;j++){
                if(arr[i]>arr[j]){
                    int max = arr[i];
                    arr[i]=arr[j];
                    arr[j]=max;
                    //break a;
                }
            }
        }
    }

    public static void paiXu1(int[] arr){
        for (int i = 0; i < arr.length-1; i++) {
            for(int j=0;j<arr.length-1-i;j++){
                if(arr[j]>arr[j+1]){
                    int max = arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=max;
                    //break a;
                }
            }
        }
    }

    public static void paiXu2(int[] arr){
        boolean flag = false;
        for (int i = 0; i < arr.length-1; i++) {
            for(int j=0;j<arr.length-1-i;j++){
                if(arr[j]>arr[j+1]){
                    flag=true;
                    int max = arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=max;
                    //break a;
                }
            }
            if(!flag){
                break;
            }else {
                flag = false;
            }
        }
    }
}
