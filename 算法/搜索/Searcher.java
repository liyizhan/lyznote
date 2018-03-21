package com.lyz.io.search;

public class Searcher {
    public static int binarySearch(int[] elements,int start,int end,int target){
        for(int i=start;i<end;i++){
            System.out.print(elements[i]+" ");    //跟踪查找轨迹
        }
        System.out.println();
        if(start>end){
            return -1;
        }
        int middle=(start+end)/2;
        if(elements[middle]==target){
            return middle;
        }else if(elements[middle]>target){
            return binarySearch(elements,start,middle-1,target);
        }else {
            return binarySearch(elements,middle+1,end,target);
        }
    }
}
