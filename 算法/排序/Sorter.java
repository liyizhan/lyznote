package com.lyz.io.sort;

public class Sorter {
    public static void bubbleSort(int[] elements){
        int temp;
        for(int i=0;i<elements.length-1;i++){
            for(int j=0;j<elements.length-1-i;j++){
                if(elements[j]>elements[j+1]){
                    temp=elements[j];
                    elements[j]=elements[j+1];
                    elements[j+1]=temp;
                }
            }
            for(int k=0;k<elements.length;k++){     //跟踪每一趟排序轨迹，实际排序不需要
                System.out.print(elements[k]+" ");
            }
            System.out.println();
        }
    }

    public static void selectSort(int[] elements){
        int min;
        int index;
        int temp;
        for(int i=0;i<elements.length-1;i++){
            min=elements[i];
            index=i;
            for(int j=i+1;j<elements.length;j++){
                if(elements[j]<min){
                    index=j;
                    min=elements[j];
                }
            }
            if(index!=i){
                temp=elements[index];
                elements[index]=elements[i];
                elements[i]=temp;
            }
            for(int k=0;k<elements.length;k++){     //跟踪每一趟排序轨迹，实际排序不需要
                System.out.print(elements[k]+" ");
            }
            System.out.println();
        }
    }

    public static void insertSort(int[] elements){
        for(int i=0;i<elements.length-1;i++){
            if(elements[i]>elements[i+1]){
                int j=i+1;
                int temp=elements[j];
                do{
                    j--;
                    elements[j+1]=elements[j];
                }while(j>0&&temp<elements[j-1]);
                elements[j]=temp;
            }
            for(int k=0;k<elements.length;k++){     //跟踪每一趟排序轨迹，实际排序不需要
                System.out.print(elements[k]+" ");
            }
            System.out.println();
        }
    }

    public static void shellSort(int[] elements,int[] increments){
        for(int i=0;i<increments.length;i++){
            System.out.println("dk:"+increments[i]);
            shellInsert(elements,increments[i]);
        }
    }

    private static void shellInsert(int[] elements,int dk){
        for(int i=0;i<elements.length-dk;i++){
            if(elements[i]>elements[i+dk]){
                int j=i+dk;
                int temp=elements[j];
                do{
                    j-=dk;
                    elements[j+dk]=elements[j];
                }while (j-dk>0&&temp<elements[j-dk]);
                elements[j]=temp;
                for(int k=0;k<elements.length;k++){     //跟踪每一趟排序轨迹，实际排序不需要
                    System.out.print(elements[k]+" ");
                }
                System.out.println();
            }
        }
    }
}
